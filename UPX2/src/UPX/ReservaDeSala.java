package UPX;

import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservaDeSala {

private String predio;
private int sala;
private String prof;
private String curso;
private String materia;
private int qtdAlunos;
private int qtdAulas;
private LocalTime horarioInicio;
private static List<ReservaDeSala> salasReservadas = new ArrayList<>();
    
 public ReservaDeSala(String predio, int sala,String prof, String curso, String materia, int qtdAlunos, int qtdAulas, String horarioInicio) {
    this.predio = predio;
    this.sala = sala;
    this.prof = prof;
    this.curso = curso;
    this.materia = materia;
    this.qtdAlunos = qtdAlunos;
    this.qtdAulas = qtdAulas;
    this.horarioInicio = LocalTime.parse(horarioInicio, DateTimeFormatter.ofPattern("HH:mm"));
}

        private LocalTime calcularHorarioTermino() {
           int duracaoTotalMinutos = (qtdAulas * 50) + (qtdAulas > 2 ? 20 : 0);
           return horarioInicio.plusMinutes(duracaoTotalMinutos);
      }

    private boolean reservaOcupada() {
        LocalTime horarioTermino = calcularHorarioTermino();
        for (ReservaDeSala reserva : salasReservadas) {
            LocalTime reservaPreenchida = reserva.horarioInicio;
            LocalTime terminoReservaExistente = reserva.calcularHorarioTermino();
            if ((horarioInicio.isBefore(terminoReservaExistente) && horarioTermino.isAfter(reservaPreenchida))) {
                return true;  
            }
        }
        return false;
    }
    
    public void reservarSala() {
        if (qtdAlunos <= 40 && qtdAulas <= 4 && sala >0 && sala <=39) {
        	if (!reservaOcupada()) {
                salasReservadas.add(this);
                LocalTime horarioTermino = calcularHorarioTermino();
                
            int minAula = 50;
            int intervalo = 20;
            int duracaoTotalMin = (qtdAulas * minAula) + intervalo;
            LocalTime horarioIntervalo = horarioInicio.plusMinutes(minAula * 2);
            horarioTermino = horarioInicio.plusMinutes(duracaoTotalMin);
            String horarioInicioF = horarioInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
            String horarioTerminoF = horarioTermino.format(DateTimeFormatter.ofPattern("HH:mm"));
            
            JOptionPane.showMessageDialog(null, "Reserva confirmada para a sala!\n" + "\n" +
            		"Predio: " + predio + "\n" +
                    "Sala: L" + sala + "\n" +
            		"Professor: " + prof + "\n" +
                    "Curso: " + curso + "\n" +
                    "Matéria: " + materia + "\n" +
                    "Quantidade de Alunos: " + qtdAlunos + "\n" +
                    "Horário: " + horarioInicioF + " - " + horarioTerminoF +
                    " (" + duracaoTotalMin / 60 + "h " + duracaoTotalMin % 60 + "min)" + 
                    " ",
                    "Reserva Confirmada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Erro! Esta sala já está reservada para o horário solicitado.",
                    "Erro de Reserva", JOptionPane.ERROR_MESSAGE);
        }
        }
        
        else {
            JOptionPane.showMessageDialog(null, "Erro na reserva! Verifique:\n" + "\n" +
                    "- A quantidade de alunos não pode exceder 40\n" +
                    "- O máximo é de 4 aulas (200 minutos)",
                    "Erro de Reserva", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ReservaDeSala r1 = new ReservaDeSala("L",21,"Prof. Ohata","ADS ","POO", 30, 4, "19:00");
        r1.reservarSala();

        ReservaDeSala r2 = new ReservaDeSala("L",21,"Prof. Ohata", "ADS","POO", 25, 2, "19:00");
        r2.reservarSala();
    }
} 


