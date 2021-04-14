package br.com.zupacademy.marcio.ecommerce.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private UsuarioRepository usuarioRepository;

    public UsuariosController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRequest usuarioRequest){
        return ResponseEntity.ok(new UsuarioResponse(usuarioRepository.save(usuarioRequest.converter())));
    }

}
