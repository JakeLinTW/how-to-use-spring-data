# Install

- Docker
    - Pull image

        ```cmd
        docker pull redis:version
        ```

    - Run container

        ```cmd
        docker run --name redis-test -p 6379:6379 -d redis
        ```

    - Enter redis container

        ```cmd
        docker exec -it container-id bash
        ```

    - redis cli

        ```cmd
        redis-cli
        ```

    - test

        ```cmd
        ping
        
        // PONG
        ```