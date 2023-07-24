package br.com.luanbraz.sendEmail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmailDetails {

    private String destinatario; // recipient

    private String corpoMensagem; // bodyMessage

    private String assunto; // subject

    private String anexo; // attachment

}
