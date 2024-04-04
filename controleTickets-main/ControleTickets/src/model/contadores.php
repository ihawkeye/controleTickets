<?php
require_once '../config/config.php';

class Contadores
{
    private $conn;

    public function __construct()
    {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function getContadores()
    {
        $query = "SELECT * FROM vendedor WHERE VENDED_INDICACAO IS NULL and QNT_INDICACOES >= 0 and INATIVO = 'N'";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $contadores = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $contador = array(
                'CODIGO' => $row['CODIGO'],
                'CODVENDED' => $row['CODVENDED'],
                'NOMEVENDED' => $row['NOMEVENDED'],
                'VENDED_INDICACAO' => $row['VENDED_INDICACAO'],
                'QNT_INDICACOES' => $row['QNT_INDICACOES']
            );
            //o $row['CODVENDED'] É O NOME DO CAMPO NO BANCO DE DADOS
            $contadores[] = $contador;
        }

        return $contadores;
    }
}

$contadores = new Contadores();
$response = $contadores->getContadores();

echo json_encode($response);
