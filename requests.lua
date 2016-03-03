
local data = string.rep("abc", 18000)

request = function()
    path = "/upload"
    wrk.headers["Content-Type"] = "multipart/form-data; boundary=--------------------31063722920652------------------------------31063722920652"
    wrk.body = data
    return wrk.format("POST", path)
end

-- /opt/wrk2/wrk -c5 -t5 -d30s -R8000 --u_latency --latency -s requests.lua  http://localhost:8080
-- sbt clean "run  redis"