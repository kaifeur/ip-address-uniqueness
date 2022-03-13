# Unique IPv4 Address Counter

Counts unique IPv4 addresses in the given file. File can be of any size. Internally uses custom bit set made of int
array of size 134217727 (512 MB). So need to have at least ~768 MB of heap space.

## How to run

```shell
gradle clean build && \
  java -Xmx768m \
  -cp ./build/libs/ip-address-uniqueness-1.0-SNAPSHOT.jar \
  nyu.rubtsov.ip.address.uniquer.Main \
  --file path/to/your/file/with/ips
```

Each line in the file should be an IPv4 address in full form (like 192.168.0.1).
