package com.devnoahf.vrumvrumhealth.Service;

import com.devnoahf.vrumvrumhealth.DTO.PacienteDTO;
import com.devnoahf.vrumvrumhealth.Mapper.PacienteMapper;
import com.devnoahf.vrumvrumhealth.Entity.Paciente;
import com.devnoahf.vrumvrumhealth.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PacienteDTO pacienteDTO;

    @Autowired
    private PacienteMapper pacienteMapper;

//    public Paciente cadastrarPaciente(Paciente paciente) {
//        // Criptografa a senha
//        String senhaCriptografada = passwordEncoder.encode(paciente.getSenhaHash());
//        paciente.setSenhaHash(senhaCriptografada);
//
//
//        // Salva no banco
//        return pacienteRepository.save(paciente);
//    }

    //método para cadastrar admin com senha criptografada
    public PacienteDTO cadastrarPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setTelefone(pacienteDTO.getTelefone());
        paciente.setRua(pacienteDTO.getRua());
        paciente.setNumero(pacienteDTO.getNumero());
        paciente.setBairro(pacienteDTO.getBairro());
        paciente.setCep(pacienteDTO.getCep());

        // Senha criptografada
        String senhaCriptografada = passwordEncoder.encode(pacienteDTO.getSenha());
        paciente.setSenha(senhaCriptografada);



        // Salvar no banco
        Paciente salvo = pacienteRepository.save(paciente);

        return pacienteMapper.toDTO(salvo);
    }

    public List<PacienteDTO> listarPaciente(){
        List<Paciente> admins = pacienteRepository.findAll();
        return admins.stream()
                .map(pacienteMapper::toDTO)
                .toList();
    }

    public void deletarPaciente(Long id){
        if (pacienteRepository.existsById(id)){
            pacienteRepository.deleteById(id);
        }
    }

    public PacienteDTO atualizarPaciente(PacienteDTO pacienteDTO, Long id){
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()){
            Paciente pacienteExistente = pacienteOptional.get();
            pacienteExistente.setNome(pacienteDTO.getNome());
            pacienteExistente.setEmail(pacienteDTO.getEmail());
            if (pacienteDTO.getSenha() != null && !pacienteDTO.getSenha().isEmpty()){
                String senhaCriptografada = passwordEncoder.encode(pacienteDTO.getSenha());
                pacienteExistente.setSenha(senhaCriptografada);
            }
            Paciente atualizado = pacienteRepository.save(pacienteExistente);
            return pacienteMapper.toDTO(atualizado);
        } else {
            return null;
        }
    }

    public PacienteDTO buscarPorIdPaciente(Long id){
        Optional<Paciente> admOptional = pacienteRepository.findById(id);
        return admOptional.map(pacienteMapper::toDTO).orElse(null);
    }

//    public void mudarSenhaPaciente(String email, String novaSenha){
//        Optional<Paciente> pacienteOptional =  Optional.ofNullableGIT(pacienteRepository.findByEmail(email));
//        if (pacienteOptional.isPresent()){
//            Paciente paciente = pacienteOptional.get();
//            String senhaCriptografada = passwordEncoder.encode(novaSenha);
//            paciente.setSenha(senhaCriptografada);
//            pacienteRepository.save(paciente);
//        }
//    }

}
