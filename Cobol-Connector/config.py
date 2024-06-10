class Config:

    Host = ""

    def __init__(self):
       pass 

    def getPath(self):
        return ("..\\output")

    def getHost(self):
        return (self.Host)
    
    def getUser(self):
        return ("root")

    def getPassword(self):
        return ("password")

    def getDatabase(self):
        return ("bank_dw")
   
    def setHost(self, aws_host):
        self.Host = aws_host
