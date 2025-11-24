import { getToken } from "./submit.js";

const authToken = await getToken();

// Buscar agendamentos aprovados
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

// Buscar paciente
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
    bairro: dados.bairro,
    cidade: dados.cidade || "",
    complemento: dados.complemento || ""
  };
}

// Buscar lista de motoristas
async function fetchMotoristas() {
  const resp = await fetch("http://localhost:8080/motorista", {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`
    }
  });

  return await resp.json(); // [{id, nome, ...}]
}

function changeValue(valor) {
  if (valor === true) return "Sim";
  if (valor === false) return "Não";
  return "";
}

// PATCH motorista
async function patchMotorista(agendamentoId, motoristaId) {
  const response = await fetch(`http://localhost:8080/agendamento/${agendamentoId}/motorista/${motoristaId}`, {
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
  preencherTabela();
}

// Montar tabela
async function preencherTabela() {
  const tbody = document.getElementById("tbodySolicitacoes");
  if (!tbody) {
    console.error("Elemento #tbodySolicitacoes não encontrado!");
    return;
  }

  tbody.innerHTML = "";

  const agendamentos = await fetchAgendamentosAprovados();
  const motoristas = await fetchMotoristas();

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

      <td>
        <select id="sel-${ag.id}" style="width:150px;">
          <option value="">Selecione...</option>
        </select>

        <button id="btn-${ag.id}">
          Atribuir
        </button>
      </td>
    `;

    tbody.appendChild(tr);

    // Preencher dropdown de motoristas
    const select = document.getElementById(`sel-${ag.id}`);
    motoristas.forEach(m => {
      const opt = document.createElement("option");
      opt.value = m.id;         // ID usado no PATCH
      opt.textContent = m.nome; // nome exibido
      select.appendChild(opt);
    });

    // Evento do botão
    const btn = document.getElementById(`btn-${ag.id}`);
    btn.addEventListener("click", () => {
      const motoristaId = select.value;

      if (!motoristaId) {
        alert("Escolha um motorista!");
        return;
      }

      patchMotorista(ag.id, motoristaId);
    });
  }
}

preencherTabela();
