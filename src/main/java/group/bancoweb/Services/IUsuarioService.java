package group.bancoweb.Services;

import group.bancoweb.dtos.UsuarioPerfilDTO;


public interface IUsuarioService {

    public UsuarioPerfilDTO obtenerPerfilPorEmail(String email);
}
