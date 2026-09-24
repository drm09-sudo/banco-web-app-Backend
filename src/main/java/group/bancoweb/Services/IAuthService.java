package group.bancoweb.Services;

import group.bancoweb.dtos.AuthResponseDTO;
import group.bancoweb.dtos.LoginRequestDTO;
import group.bancoweb.dtos.RegistroDTO;

public interface IAuthService {
    public AuthResponseDTO login(LoginRequestDTO dto);
    public AuthResponseDTO registro(RegistroDTO dto);


}
