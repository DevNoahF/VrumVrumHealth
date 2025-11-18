

authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjM1MDQ4NDAsImV4cCI6MTc2MzU5MTI0MH0.FLKTQCA_z7S3YfpytFNWymEb8-hnMVQkfQrxyAqef1-_zZOxJaDIdMMJ6M6ebJlTT203tHoq2pTKHjjKka0SIA"
async function getAgendamentosPendentes() {
  const response = await fetch("http://localhost:8080/agendamento", {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`,
    },
  });
  const dados = await response.json();
  return dados
}

async function pacienteData(id) {
  const response = await fetch(`http://localhost:8080/paciente/${id}`, {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`,
    },
  });
  const dados = await response.json();
  return {
    nome: dados.nome,
    dataNasc: dados.dataNascimento,
    cpf: dados.cpf,
    bairro: dados.bairro,
    rua: dados.rua,
    numero: dados.numero,
  };
}

function changeValue(valor) {
  if (valor === true) return "Sim";
  if (valor === false) return "Não";
  return "Erro, dado não encontrado";
}

async function atualizarStatus(id, novoStatus) {
  const response = await fetch(`http://localhost:8080/agendamento/${id}`, {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`,
    },
  });
  const agendamento = await response.json();
  agendamento.statusEnum = novoStatus;

  await fetch(`http://localhost:8080/agendamento/${id}`, {
    method: "PUT",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`,
    },
    body: JSON.stringify(agendamento),
  });

  alert(`Agendamento #${id} ${novoStatus.toLowerCase()} com sucesso!`);
  // opcional: recarregar a lista
  carregarLista();
}

async function carregarLista() {
  const container = document.getElementById("lista-pedidos");
  container.innerHTML = ""; // limpa lista existente

  const agendamentos = await getAgendamentosPendentes();
  for (const ag of agendamentos) {
    const pac = await pacienteData(ag.pacienteId);

    const div = document.createElement("div");
    div.classList.add("pedido-transporte");
    div.innerHTML = `
      <div class="pedido">
        <p>id: ${ag.id}</p>
        <p>Nome: ${pac.nome}</p>
        <p>Data Marcada: ${ag.dataConsulta} - ${ag.horaConsulta}</p>
        <p>Data Nascimento: ${pac.dataNasc}</p>
        <p>CPF: ${pac.cpf}</p>
        <p>Endereço: ${pac.rua} ${pac.numero} ${pac.bairro}</p>
        <p>Motivo Transporte: ${ag.tipoAtendimentoEnum}</p>
        <p>Unidade médica: ${ag.localAtendimentoEnum}</p>

        <p class="dir">Frequência: ${ag.frequencia}</p>
        <p class="dir">Acompanhante?: ${changeValue(ag.acompanhante)}</p>
        <p class="dir">Precisa de transporte na volta?: ${changeValue(ag.retornoCasa)}</p>
      </div>
      <div class="botoes">
        <button id="aprovar">Aprovar</button>
        <button id="recusar">Recusar</button>
        <button id="anexo">Visualizar Anexo</button>
      </div>
    `;

    // Adiciona os event listeners
    div.querySelector("#aprovar").addEventListener("click", () => atualizarStatus(ag.id, "APROVADO"));
    div.querySelector("#recusar").addEventListener("click", () => atualizarStatus(ag.id, "NEGADO"));
    div.querySelector("#anexo").addEventListener("click", () => {
      if (ag.comprovante) {
        window.open(ag.comprovante, "_blank");
      } else {
        alert("Nenhum comprovante disponível.");
      }
    });

    container.appendChild(div);
  }
}

// Inicializa
document.addEventListener("DOMContentLoaded", () => {
  carregarLista();
});
