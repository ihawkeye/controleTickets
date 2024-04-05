<?php

class Database {
    private $host;
    private $usuario = 'SYSDBA';
    private $senha = 'masterkey';
    private $conn;
    
    public function getConnection() {
        // Cria a conexão
        try {
            $this->conn = new PDO(
                "firebird:dbname=localhost:C:\CPlus\CPLUS.FDB;charset=UTF8",
                $this->usuario,
                $this->senha
            );
        } catch(PDOException $e) {
            echo "Falha na conexão." . $e->getMessage();
        }
        
        return $this->conn;
    }

		public function setHost($caminho){
			$this->host = $caminho;
		}


	}	

?>