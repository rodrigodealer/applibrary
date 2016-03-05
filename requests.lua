
counter = 0
local data = string.rep("" .. math.random(1000,1000), 18000)

request = function()
    path = "/docs"
    wrk.headers["Content-Type"] = "multipart/form-data; boundary=--------------------31063722920652------------------------------31063722920652"
    wrk.headers["X-Tradeshift-TenantId"] = "gm"
    counter = counter + 1
    wrk.body = data .. counter .. math.random(10000000000)
    return wrk.format("POST", path)
end

-- /opt/wrk2/wrk -c5 -t5 -d30s -R8000 --u_latency --latency -s requests.lua  http://localhost:8080
-- sbt clean "run  redis"