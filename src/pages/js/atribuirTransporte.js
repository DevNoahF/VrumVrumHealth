import { getToken } from "./submit.js";

const authToken = await getToken();

async function fetchAgendamentosAprovados() {
  const response = await fetch("http://localhost:8080/agendamento", {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`
    }
  });
  const dados = await response.json();
  return dados.filter(a => a.statusComprovanteEnum === "APROVADO");
}

async function fetchPaciente(id) {
  const resp = await fetch(`http://localhost:8080/paciente/${id}`, {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`
    }
  });
  const dados = await resp.json();
  return {
    nome: dados.nome,
    rua: dados.rua,
    numero: dados.numero,
    bairro: dados.bairro,
    cidade: dados.cidade || "",
    complemento: dados.complemento || ""
  };
}

function changeValue(valor) {
  if (valor === true) return "Sim";
  if (valor === false) return "Não";
  return "";
}

async function patchMotorista(agendamentoId, motoristaId) {
  const response = await fetch(`http://localhost:8080/agendamento/${agendamentoId}`, {
    method: "PATCH",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`
    },
    body: JSON.stringify({
      motoristaId: motoristaId
    })
  });

  if (!response.ok) {
    alert("Erro ao atualizar motorista!");
    return;
  }

  alert("Motorista atribuído com sucesso!");
  preencherTabela(); // recarregar tabela
}

async function preencherTabela() {
  const tbody = document.getElementById("tbodySolicitacoes");
  if (!tbody) {
    console.error("Elemento #tbodySolicitacoes não encontrado!");
    return;
  }
  tbody.innerHTML = "";

  const agendamentos = await fetchAgendamentosAprovados();

  for (const ag of agendamentos) {
    const pac = await fetchPaciente(ag.pacienteId);

    let tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${ag.id}</td>
      <td>${pac.nome}</td>
      <td>${pac.rua}</td>
      <td>${pac.bairro}</td>
      <td>${ag.localAtendimentoEnum}</td>
      <td>${ag.dataConsulta} ${ag.horaConsulta}</td>
      <td>${changeValue(ag.acompanhante)}</td>
      <td>${changeValue(ag.retornoCasa)}</td>

      <!-- Campo para motorista + botão -->
      <td>
        <input type="number" min="1" id="motorista-${ag.id}"
               placeholder="ID motorista" style="width: 120px;">
        <button id="btn-${ag.id}">Atribuir</button>
      </td>
    `;

    tbody.appendChild(tr);

    // Adiciona evento ao botão
    const btn = document.getElementById(`btn-${ag.id}`);
    btn.addEventListener("click", () => {
      const motoristaId = document.getElementById(`motorista-${ag.id}`).value;

      if (!motoristaId) {
        alert("Informe o ID do motorista!");
        return;
      }

      patchMotorista(ag.id, motoristaId);
    });
  }
}

preencherTabela();
