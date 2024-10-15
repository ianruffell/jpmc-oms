1. Start Oracle Docker service

```bash
docker run -d -p 1521:1521 -e ORACLE_PASSWORD=oracle gvenzl/oracle-free
```

2. Load sample data from data/co_install.sql
3. Start GridGain Server - com.gridgain.demo.Server
4. Start GridGain client - com.gridgain.demo.AppCustomerJDBCCacheStore
