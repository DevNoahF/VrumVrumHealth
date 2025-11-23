import { getToken } from "./submit.js";
const authToken = await getToken();

async function getAgendamentosPendentes() {
  const response = await fetch("http://localhost:8080/agendamento", {
    method: "GET",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`,
    },
  });

  if (!response.ok) {
    throw new Error(`Erro ao buscar agendamentos: ${response.status}`);
  }

  const dados = await response.json();
  return dados.filter(a => a.statusComprovanteEnum === "PENDENTE");
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

  if (!response.ok) {
    throw new Error(`Erro ao buscar paciente ${id}: ${response.status}`);
  }

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
  try {
    // 1. Buscar o agendamento atual
    const getResponse = await fetch(`http://localhost:8080/agendamento/${id}`, {
      method: "GET",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${authToken}`,
      },
    });

    if (!getResponse.ok) {
      throw new Error(`Erro ao obter agendamento ${id}: ${getResponse.status}`);
    }

    // 2. Ler o corpo da resposta com segurança
    const getText = await getResponse.text();
    let agendamento;
    if (getText) {
      try {
        agendamento = JSON.parse(getText);
      } catch (err) {
        console.error("Erro ao fazer parse do agendamento:", getText, err);
        throw new Error("Resposta GET inválida");
      }
    } else {
      console.warn("Resposta GET veio vazia para o agendamento", id);
      // Se vier vazia, depende do seu backend: você pode decidir não continuar
      return;
    }

    // 3. Alterar o status
    agendamento.statusComprovanteEnum = novoStatus;

    // 4. Enviar o PUT com o agendamento atualizado
    const putResponse = await fetch(`http://localhost:8080/agendamento/${id}`, {
      method: "PUT",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${authToken}`,
      },
      body: JSON.stringify(agendamento),
    });

    if (!putResponse.ok) {
      throw new Error(`Erro ao atualizar agendamento ${id}: ${putResponse.status}`);
    }

    // 5. Tentar ler a resposta do PUT (se houver corpo)
    const putText = await putResponse.text();
    if (putText) {
      try {
        const respostaAtualizada = JSON.parse(putText);
        console.log("Agendamento atualizado:", respostaAtualizada);
      } catch (err) {
        console.warn("Resposta PUT não é JSON válido:", putText);
        // Se não for JSON, pode ignorar ou tratar de outro jeito
      }
    }

    alert(`Agendamento #${id} ${novoStatus.toLowerCase()} com sucesso!`);

    // Recarrega a lista
    carregarLista();
  } catch (erro) {
    console.error("Falha em atualizar status:", erro);
    alert("Ocorreu um erro ao atualizar o status. Veja o console para mais detalhes.");
  }
}

async function carregarLista() {
  const container = document.getElementById("lista-pedidos");
  container.innerHTML = "";

  let agendamentos;
  try {
    agendamentos = await getAgendamentosPendentes();
  } catch (erro) {
    console.error("Erro ao carregar agendamentos:", erro);
    container.innerHTML = "<p>Erro ao carregar solicitações.</p>";
    return;
  }

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
        <button class="aprovar">Aprovar</button>
        <button class="recusar">Recusar</button>
        <button class="anexo">Visualizar Anexo</button>
      </div>
    `;

    const btnAprovar = div.querySelector(".aprovar");
    const btnRecusar = div.querySelector(".recusar");
    const btnAnexo = div.querySelector(".anexo");

    if (btnAprovar) {
      btnAprovar.addEventListener("click", () => atualizarStatus(ag.id, "APROVADO"));
    }
    if (btnRecusar) {
      btnRecusar.addEventListener("click", () => atualizarStatus(ag.id, "NEGADO"));
    }
    if (btnAnexo) {
      btnAnexo.addEventListener("click", () => {
        if (ag.comprovante) {
          window.open(ag.comprovante, "_blank");
        } else {
          alert("Nenhum comprovante disponível.");
        }
      });
    }

    container.appendChild(div);
  }
}

// Inicializa

carregarLista();
