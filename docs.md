FORMAT: 1A
HOST: https://localhost

# Tradeshift Docs API
[Tradeshift](http://tradeshift.com/) for storing and managing documents.

## Disclaimer
Just a toy for the devsync

# Group Docs 

## Operations [/docs]

### Create New Doc [POST] 
Create a new doc posting file with X-Tradeshift-TenantId header

+ Request (multipart/form-data; boundary=--)
    
    + Headers
        X-Tradeshift-TenantId: 9877531B-A291-4597-A5FE-908B999EE995

    + Body
        Foo Bar Bar

+ Response 201

    + Headers
        ts-id: 16A07D54-ED74-47EE-AB31-87A3E28674A8