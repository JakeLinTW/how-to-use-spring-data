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