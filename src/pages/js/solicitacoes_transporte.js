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

/**
 * Atualiza apenas o campo de status usando PATCH.
 * Envia um body mínimo { statusComprovanteEnum: novoStatus }.
 * Aceita resposta 200/204 com/sem corpo.
 */
async function atualizarStatus(id, novoStatus) {
  try {
    if (!id) {
      throw new Error("ID do agendamento inválido");
    }
    if (!authToken) {
      throw new Error("Token de autenticação ausente");
    }

    // Body mínimo para PATCH — evita enviar todo o recurso

    const patchResponse = await fetch(`http://localhost:8080/agendamento/status/${id}`, {
      method: "PATCH",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${authToken}`,
      },
      body: JSON.stringify(novoStatus),
    });

    if (!patchResponse.ok) {
      // tenta ler corpo de erro para dar mais contexto
      let detalhe = "";
      try {
        detalhe = await patchResponse.text();
        if (detalhe) {
          // limitar tamanho do detalhe no log
          console.error("Detalhe do erro PATCH:", detalhe.slice(0, 200));
        }
      } catch (e) {
        console.warn("Não foi possível ler corpo de erro do PATCH.");
      }
      throw new Error(`Erro ao atualizar agendamento ${id}: ${patchResponse.status}`);
    }

    // PATCH pode retornar 204 No Content ou 200 com JSON; tentamos ler com segurança
    let respostaAtualizada = null;
    const text = await patchResponse.text();
    if (text) {
      try {
        respostaAtualizada = JSON.parse(text);
        console.log("Agendamento atualizado (body):", respostaAtualizada);
      } catch (err) {
        console.warn("Resposta PATCH não é JSON válido:", text);
      }
    } else {
      console.log("PATCH retornou sem corpo (204).");
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
