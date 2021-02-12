# minhas-tarefas

Pequena API com Spring.

<div>
  <b>Rotas</b>
  <p>
    <b>POST: </b> http://localhost:8080/minhastarefas/api/auth/login<br>
    <b>JSON</b><br>
    {
      "nome": "Admin",
      "senha": "123456"
    }
    <br><br>
    <b>POST: </b> localhost:8080/minhastarefas/categoria<br>
    <b>JSON</b>
    <p>
      {
        "nome": "teste 001"
      }
    <br>
    <br>
    <b>POST:</b> localhost:8080/minhastarefas/tarefa<br>
    <b>JSON:</b>
    	{
        "descricao": "lalalal",
        "status": "ABERTO",
        "dataEntrega": "2021-02-12",
        "visivel": true,
        "categoria": {
            "id": 2,
            "nome": "Estudos"
        },
        "usuario": {
            "id": 1,
            "nome": "Admin da Silva",
            "senha": "12456"
        }
    }
<br><br>
    <b>DELETE:</b>localhost:8080/minhastarefas/usuario/6<br><br>
    <b>DELETE:</b>localhost:8080/minhastarefas/categoria/5<br><br>
    <b>DELETE:</b>localhost:8080/minhastarefas/tarefa/4<br><br>
    <b>PUT:</b>localhost:8080/minhastarefas/tarefa/3/cancelar<br>
    <b>GET</b>
    URL:localhost:8080/minhastarefas/tarefa
   </p>
</div>
