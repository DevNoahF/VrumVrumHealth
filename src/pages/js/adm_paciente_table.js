import { getToken } from "./submit.js";

var exportar = document.getElementById("exportar")
const authToken=await getToken()

//Função que permite exportar tabela para pdf
exportar.addEventListener("click",function(){
   const { jsPDF } = window.jspdf;
    const doc = new jsPDF({
      orientation: 'landscape', 
      unit: 'pt',
      format: 'a4'
    });

    // Seleciona a tabela toda
    const tableElement = document.querySelector('.solicitacoes-table');

    // Permite editar a base do PDF para qualquer forma
    doc.autoTable({
      html: tableElement,
      startY: 40, 
      theme: 'striped', 
      headStyles: { fillColor: [41, 128, 185] },
      styles: { fontSize: 10 },

    });

    doc.save('Solicitações Aprovadas.pdf')
})



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
  // Filtrar apenas aprovados
  return dados//.filter(a => a.statusEnum === "APROVADO");
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
    cidade: dados.cidade || "",        // se existir
    complemento: dados.complemento || "" // se existir
  };
}

function changeValue(valor) {
  if (valor === true) return "Sim";
  if (valor === false) return "Não";
  return "";
}

async function preencherTabela() {
  const tbody = document.getElementById("tbodySolicitacoes");
  if (!tbody) {
    console.error("Elemento #tbodySolicitacoes não encontrado!");
    return;
  }
  tbody.innerHTML = ""; // limpa conteúdo antigo

  const agendamentos = await fetchAgendamentosAprovados();

  for (const ag of agendamentos) {
    const pac = await fetchPaciente(ag.pacienteId);

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${pac.nome}</td>
      <td>${pac.rua}</td>
      <td>${pac.bairro}</td>
      <td>${ag.localAtendimentoEnum}</td>
      <td>${ag.dataConsulta} ${ag.horaConsulta}</td>
      <td>${changeValue(ag.acompanhante)}</td>
      <td>${changeValue(ag.retornoCasa)}</td>
    `;
    tbody.appendChild(tr);
  }

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${ag.id}</td>
      <td>${pac.nome}</td>
      <td>${pac.rua}</td>
      <td>${pac.bairro}</td>
      <td>${pac.cidade}</td>
      <td>${pac.complemento}</td>
      <td>${ag.localAtendimentoEnum}</td>
      <td>${ag.dataConsulta} ${ag.horaConsulta}</td>
      <td>${changeValue(ag.acompanhante)}</td>
      <td>${changeValue(ag.retornoCasa)}</td>
      <td>${changeValue(ag.necessidadeEspecial)}</td>
    `;
    tbody.appendChild(tr);
  }
    


document.addEventListener("DOMContentLoaded", () => {
  preencherTabela();
  fetchAgendamentosAprovados()

});
