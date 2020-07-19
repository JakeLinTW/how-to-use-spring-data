# Commands

## KEY 

1. KYES pattern
    - 搜尋 Redis 存在的 Key 值
    - 會 Block Reids 直到搜尋完成，如果有幾百萬個 Key 會造成整個 Redis 卡死一段時間
    - 建議不要在 Production 使用這個指令
    - 使用上比較簡單，通常用在 debug
    - 可使用 * 號進行模糊查詢
    - Example
        - KEYS customer:*
        - KEYS customer:1000

2. SCAN cursor [MATCH pattern] [COUNT count]
    - 使用 Cursor 查詢 Redis 存在的 Key 值
    - 也會 Block，但一次只回傳 0 到指定數量的 Key
    - Safe for production
    - Slot 放入查詢回傳的 Cursor ，重複查詢直到 Cursor 回到 0 結束 Iterate
    - Example
        - SCAN 0
        - SCAN 196608
        - SCAN 0 MATCH customer:*
        - SCAN 1286144 MATCH customer:*
        - SCAN 0 MATCH customer:* COUNT 1000

3. EXISTS key [key ...]
    - 檢查 Key 是否存在

4. DEL key [key ...]
    - Blocking operation
    - 直接刪除 Key

5. UNLINK key [key ...]
    - Asynchronous process, non-blocking command.
    - 取消 Key 關聯，異步回收 Memory

6. TYPE key
    - 檢查 Key 型別

7. OBJECT ENCODING key
    - 檢查 Value 編碼

<br>
<br>
<br>

## TTL

1. Set
    - EXPIRE key seconds
        - 指定到期時間( 秒 )

    - EXPIREAT key timestamp
        - 指定到期時間戳( unix seconds timestamp )

    - PEXPIRE key milliseconds
        - 指定到期時間( 豪秒 )

    - PEXPIREAT key milliseconds-timestamp
        - 指定到期時間戳( unix milliseconds timestamp )

2. Inspect
    - PTTL Key
        - 檢查過期時間( 毫秒 )

    - TTL Key
        - 檢查過期時間( 秒 )

3. Remove
    - PERSIST Key
        - 刪除到期時間

<br>
<br>
<br>

## String

1. SET key value [EX seconds | PX milliseconds] [NX | XX]
    - 設定 Key value pair
    - EX
        - 過期時間( 秒 )
        - Equals : SETEX key seconds value

    - PX
        - 過期時間( 毫秒 )
        - Equals : PSETEX key milliseconds value

    - NX
        - Key 不存在才執行操作
        - Equals : SETNX key value

    - XX
        - Key 已存在才執行操作
    
    - Example
        - SET customer:1
        - SET customer:1 EX 60
        - SET customer:1 PX 60
        - SET customer:1 EX 5 XX

2. GET key
    - 取得對應的 Value

3. DECRBY key decrement
    - 整數遞減，可為負數
    - Key 不存在時自動建立
    - 遞減 1 縮寫 : DECR key

4. INCRBY key increment
    - 整數遞增，可為負數
    - Key 不存在時自動建立
    - 遞增 1 縮寫 : INCR key

5. INCRBYFLOAT key increment
    - 浮點數操作，可為負數
    - Key 不存在時自動建立

<br>
<br>
<br>

# Hash

1. HSET key field value [field value ...]
    - 設定 Hash key 及屬性與屬性值
    - 可一次設定多個屬性
    - Example
        - HSET stationery pen 10 notebook 30

2. HMSET key field value [field value ...]
    - 同時設定多個 Field value
    - 較早期的版本 HSET 不可同時設定多個 Field value，需要使用 HMSET

2. HSETNX key field value
    - Field 不存在才執行操作
    - Key 不存在則建立一個新的 Hash 表

3. HGET key field
    - 取得指定 field 對應的 Value

4. HMGET key field [field ...]
    - 取得多個 fields 對應的 Value

5. HGETALL key
    - 返回所有 Field value，不建議在 Production 使用
    - 奇數行為 Field，偶數行為 Value，所以返回長度會是此 Hash 的兩倍

6. HKEYS key
    - 取得所有 Field，不建議在 Production 使用

7. HVALS key
    - 取得所有 Value，不建議在 Production 使用

6. HSCAN key cursor [MATCH pattern] [COUNT count]
    - 使用 Cursor 搜尋指定的 pattern
    - example HSCAN stationery 0 pen*

7. HEXISTS key field
    - 檢查 Field 是否存在

8. HDEL key field [field ...]
    - 刪除 Field

9. HINCRBY key field increment
    - 遞增指定 Field 的 Value ( 整數 )
    - Key or field 不存在時自動建立

10. HINCRBYFLOAT key field increment
    - 遞增指定 Field 的 Value ( 浮點數 )
    - Key or field 不存在時自動建立

<br>
<br>
<br>

# List

1. Push
    - 添加 Value 到指定 List
        - LPUSH key value [value ...]
        - RPUSH key value [value ...]

2. Pop
    - 取出並移除指定 List 的 Value
        - LPOP key
        - RPOP key

3. Length
    - 查詢指定 List 長度
        - LLEN key

4. Range
    - 查詢指定範圍的 Value
        - LRANGE key start stop
            - 使用 0 -1 查詢所有資料
            - Example
                - LRANGE customer 0 -1

5. Index
    - 查詢指定 Index 的 Value
        - LINDEX key index

6. Insert
    - 在指定位置插入 Value
        - LINSERT key BEFORE pivot value
        - LINSERT key AFTER pivot value

7. Set
    - 在指定的 Index 插入 Value
        - LSET key index value

8. Remove
    - 刪除指定數量的 Value
        - LREM key count value
    
    - 只保留指定區域的資料
        - LTRIM key count value

<br>
<br>
<br>

# Set

1. SADD key member [member ...]
    - 加入新成員

2. SMEMBERS key
    - 取得所有成員，不建議在 Production 使用

3. SSCAN key cursor [MATCH pattern] [COUNT count]
    - 使用 Cursor 搜尋指定的 pattern
    - Example
        - SSCAN customer 0 MATCH *

4. SISMEMBER key member
    - 檢查成員是否存在

5. SREM key member [member ...]
    - 指定刪除某成員

6. SPOP key [count]
    - 刪除並返回一個隨機成員

7. 比較集合
    - 受到操作的 Set 基數影響，應該避免在龐大的集合中使用
        - SDIFF key [key ...]
            - 返回最左邊的 Key 的成員與其他 Key 的成員不重複的部分，並非差集
            - Equals : SDIFFSTORE

        - SINTER key [key ...]
            - 取得所有成員重疊的部分
            - 避免在龐大的集合中使用
            - Equals : SINTERSTORE

        - SUNION key [key ...]
            - 取得所有不重複的成員
            - 避免在龐大的集合中使用
            - Equals : SUNIONSTORE