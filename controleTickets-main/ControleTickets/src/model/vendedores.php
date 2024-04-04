<?php
require_once '../config/config.php';

class Vendedores {
    private $conn;

    public function __construct() {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function getVendedores() {
        $query = "SELECT * FROM vendedor where QNT_INDICACOES = -1";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
    
        $vendedores = array();
    
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $vendedor = array(
                'CODIGO' => $row['CODIGO'],
                'CODVENDED' => $row['CODVENDED'],
                'NOMEVENDED' => $row['NOMEVENDED'],
                'QNT_INDICACOES' => $row['QNT_INDICACOES'],
                'VENDED_INDICACAO' => $row['VENDED_INDICACAO']
            );
            //o $row['CODVENDED'] É O NOME DO CAMPO NO BANCO DE DADOS
            $vendedores[] = $vendedor;
        }
    
        return $vendedores;
    }
}

$vendedores = new Vendedores();
$response = $vendedores->getVendedores();

echo json_encode($response);
?>
