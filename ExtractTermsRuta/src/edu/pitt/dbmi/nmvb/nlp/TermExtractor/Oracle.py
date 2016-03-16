"""Created on Sept 24, 2015
    @author: del20"""

class Oracle:
    def __init__(self, host, port, SID, user, password, db=None):
        self.host = host
        self.port = port
        self.SID = SID
        self.user = user
        self.password = password
        self.db = db
        self.connection = None
        
    def getConnection(self):
        if not self.connection:
            import cx_Oracle
            dsn_tns = cx_Oracle.makedsn(self.host, self.port, self.SID)
            self.connection = cx_Oracle.connect(self.user, self.password, dsn_tns)
        return self.connection
    
    def getFullyQualifyTableName(self, tableName):
        tableFullName = tableName
        if self.db:
            tableFullName = self.db + "." + tableName
        print tableFullName
        return tableFullName
        
    def close(self):
        if (self.connection):
            self.connection.close()

    def escape(self, tableName):
        return tableName
