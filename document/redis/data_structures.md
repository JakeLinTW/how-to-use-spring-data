# Data structures

1. Keys
    - Unique
    - Binary safe
        - Redis 可使用任意字元當作 Key 值

    - 可使用空格，但 KEY 必須是帶有雙引號的字符串
        - e.g. set "this is a key"

    - Up to 512 MB
        - Redis 支援超長的 Key 值，且未來可能會再增加，建議在長度與可讀性取得平衡

    - 不保留空值的 Key
        - 當資料結構為 Hash, List, Set, Sorted Set 時，若將所有的值刪除，使用 EXISTS 查詢會返回 0 ( 不存在 )

    - Logical database
        - 一個 Redis 可以有多個邏輯數據庫
        - 不同邏輯數據庫的 Key 值可以重複，可在單一 Application 中分離 Key 值，而不用拆成多個 Application
        - Redis 沒有傳統數據庫的概念，所有資料都存放在同一個 RDB / AOF 文件中
        - Redis 希望所有事情都是很簡單的，避免了 Namespace 管理的複雜性
        - database index 由 0 開始，預設使用 database 0
        - Redis cluster 只支援 database 0
        - 大部分工具與框架都假設使用 database 0

    - Name
        - user:id:follows
            - user:1000:follows
                - user : object name
                - 1000 : unique identity of the instance
                - follows : composed object
        
        - 上面這是 Redis 社群常見的命名方式，實際使用依照團隊約定好的一致性命名即可
        - 區分大小寫
            - user:1000:follows 與 User:1000:Follows 是不同的

    - Expiration
        - Redis 會將資料保存在內存中直到空間用完為止
        - 可設定 TTL ( time to live ) 指定過期時間
        - TTL 可自由變更

    - Plymorphism
        - 同一個 Key 可以放入不同資料型別的 Value

2. Data structures
    - String
        - Text data
        - Integer and Floating Point number
            - Redis 對數字操作順序如下
                1. 接收 INCR 之類數字操作指令
                2. 檢查 Key 型別是否正確
                3. 檢查 Value encoding
                4. 改變 value

        - Binary data
        - Maximum of 512 MB
        - 不管是哪種狀態儲存，都是用 String data 儲存

    - Hash
        - Key with name fields
            - 類似用 Key 分組後的 mini key value pair
            - { key: { field1 : value1, field2 : value2 } }

        - Single level
        - 提供特定的指令操作 Fields
        - 可動態新增或移除 Fields，為了有效率的操作大量資料，基本上沒有結構
        - 所有資料都只能用 String 來儲存，沒有嵌套概念，Fields 不能設定 list, set, sorted set, hash
        - 刪除 Key，相關 Fields 都會被跟著刪除
        - Use cases
            - Rate limiting
                - 如下建立每日 Api 訪問上限

                    ```cmd
                    HMSET date:20200101 "/booking/{id}" 100
                    ```

                - 每次訪問檢查剩餘次數並遞減 1

                    ```cmd
                    HINCRBY date:20200101 "/booking/{id}" -1
                    ```

                - 設定過期時間，避免佔用記憶體

                    ```cmd
                    EXPIRE date:20200101 86400
                    ```

            - Session cache
                - 儲存跨服務的 Session

                ```cmd
                HMSET session:3a6r ts 1518132000 host 127.0.0.1
                ```

                - 記錄訪問次數

                ```cmd
                HINCRBY session:3a6r requests 1
                ```

                - 設定 Session 到期時間

                ```cmd
                EXPIRE session:3a6r 1200
                ```

    - List
        - 依照插入順序排序的 String 集合
        - 所有資料都只能用 String 來儲存，沒有嵌套概念，不能設定 list, set, sorted set, hash
        - 可以有重複的值
        - 沒有頭尾概念，對 Reids 來說，可選擇由左右任一側插入值
        - 使用雙向鏈表，基於 Linked list 實作，提供了大量數據的性能解決方案
        - 不建議頻繁的使用 Index 操作，可能會有效能上的問題
        - 可使用在實現 Stack 或 Queue 功能
        - User cases
            - Activity stream
                - 加入活動列表

                    ```cmd
                    LPUSH stream 1 2 3 4 5
                    ```

                - 檢查活動

                    ```cmd
                    LRANGE stream 0 2
                    ```

                - 保留最新的活動

                    ```cmd
                    LTRIM stream 0 3
                    ```

            - producer-consumer pattern
                - Inter process communication
                    - Produce

                        ```cmd
                        RPUSH queue "event1"
                        ```
                    - Consume

                        ```cmd
                        LPOP queue
                        ```

    - Set
        - 不重複的 String 集合
        - 所有資料都只能用 String 來儲存，沒有嵌套概念，不能設定 list, set, sorted set, hash
        - 忽略順序
        - 支援集合操作
            - A difference B
                - 取得 A 與 B 不重複的部分，並非差集

            - A intersection B
                - 取得兩個 Set 重疊的部分

            - A union B
                - 取得兩個 Set 的所有值，並去除重複
        
        - Use cases
            - Tag cloud
                - 加入 Tag

                    ```cmd
                    SADD wrench tool metal

                    SADD coin currency metal
                    ```

                - 查詢 Tag

                    ```cmd
                    SSCAN wrench 0 match *
                    ```

                - 查找兩樣東西的共通點

                    ```cmd
                    SINTER wrench coin
                    ```

                - 查詢已建立的 Tag

                    ```cmd
                    SUNION wrench coin
                    ```

            - Unique visitor
                - 使用時間記錄每個頁面每日不重複的訪客

                    ```cmd
                    SADD index.html:20200101 a b c
                    ```

                - 搜尋當日訪客

                    ```cmd
                    SSCAN index.html:20200101 0 match *
                    ```

                - 設定過期時間

                    ```cmd
                    EXPIRE index.html:20200101 86400
                    ```

    - Sorted Set
        - 有順序的 Set
        - 所有資料都只能用 String 來儲存，沒有嵌套概念，不能設定 list, set, sorted set, hash
        - Sorting order by floating point score
            - 若分數一樣則比較 Lex order

        - 依照 value, position, rank, lexicography 進行操作
        - 支援集合操作，但只能將結果儲存到另一個 Sorted Set 中
            - Union
            - Intersection

        - Use cases
            - Leaderboards
                - 加入排行榜

                    ```cmd
                    ZADD lb 90 a 100 b 3 c 60 d 22 e
                    ```

                - 得分時增加分數

                    ```cmd
                    ZINCRBY lb 50 c
                    ```

                - 查詢前三高分

                    ```cmd
                    ZREVRANGE lb 0 2 WITHSCORES
                    ```

                - 保留前三名分數

                    ```cmd
                    ZREMRANGEBYRANK lb 0 -4
                    ```

            - Priority quere
                - 加入任務

                    ```cmd
                    ZADD pq 1 p1 2 p2 3 p3 1 p1-2
                    ```

                - 取出最優先的任務

                    ```cmd
                    ZRANGE pq 0 0
                    ```

                - 刪除已完成任務

                    ```cmd
                    ZREM pq p1
                    ```