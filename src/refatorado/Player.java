package refatorado;
import refatorado.Main;

public class Player {
	
	int player_state;								// estado
	double player_X;								// coordenada x
	double player_Y;								// coordenada y
	double player_VX;								// velocidade no eixo x
	double player_VY;								// velocidade no eixo y
	double player_radius;							// raio (tamanho aproximado do player)
	double player_explosion_start;					// instante do início da explosão
	double player_explosion_end;					// instante do final da explosão
	long player_nextShot;							// instante a partir do qual pode haver um próximo tiro


	Player(){
		player_state = Main.ACTIVE;
		player_X = GameLib.WIDTH / 2;
		player_Y = GameLib.HEIGHT * 0.90;
		player_VX = 0.25;
		player_VY = 0.25;
		player_radius = 12.0;
		player_explosion_start = 0;
		player_explosion_end = 0;
		player_nextShot = Main.currentTime;
	}

}


