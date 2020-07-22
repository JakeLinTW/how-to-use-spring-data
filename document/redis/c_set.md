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