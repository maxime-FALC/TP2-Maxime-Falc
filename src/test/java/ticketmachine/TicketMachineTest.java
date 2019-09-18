package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}

        
        @Test
        // S3 : On n'imprime pas le ticket si le montant est insuffisant
        public void noImpressionOfTicketWithoutEnoughMoney() {
            
            // demande d'impression sans fonds suffisants
            assertFalse("Le ticket a été imprimé sans un montant suffisant", machine.printTicket());
        }
        
        
        @Test
        // S4 : On imprime le tickete quand le montant est suffisant
        public void ticketImpressionWithEnoughMoney() {
            
            // ajout des fonds pour l'impression
            machine.insertMoney(PRICE);
            
            //impression du ticket
            assertTrue("Le ticket n'a pas été imprimé", machine.printTicket());
        }
        
        
        
        @Test
        // S5 : On vérifie qu'après l'impression, la machine a bien retiré les fonds du ticket
        public void checkDecreaseMoneyAfterImpression() {
            
            // ajout des fonds pour la balance avec supplément financier
            machine.insertMoney(PRICE);
            machine.insertMoney(10);
            
            // impression du ticket
            machine.printTicket();
            
            // vérification de la balance financière
            assertEquals("Le prix du ticket n'a pas été suffisamment enlevé ou l'a trop été", 10, machine.getBalance());
        }
        
        
        @Test
        // S6 : le montant collecté est mis à jour APRES impression du tiket
        public void checkBalanceDecrease() {
            
            //test montant total de base valide
            assertEquals("Le montant total n'est pas valide de base", 0, machine.getTotal());
            
            // ajout des fonds pour la balance 
            machine.insertMoney(PRICE);
            
            // impression d'un ticket
            machine.printTicket();
            
            // ajout d'argent
            machine.insertMoney(20);
            machine.insertMoney(PRICE);
            
            //impression d'un ticket
            machine.printTicket();
            
            // vérification que le retrait a eu lieu juste après l'impression du ticket
            assertEquals("Le montant total n'est pas valide", 120, machine.getTotal());
        }
        
        
        
        @Test
        // S7 : vérification que la fonction pour rendre la monnaie est correcte
        public void refundIsCorrect() {
            
            //ajout financier
            machine.insertMoney(PRICE);
            
            // test que le remboursement soit correct sans impression
            assertEquals("La machine n'a pas rendu le bon montant avant impression", PRICE, machine.refund());
            
            
            // ajout financier pour ticket + supplement financier
            machine.insertMoney(PRICE);
            machine.insertMoney(20);
            
            //impression d'un ticket
            machine.printTicket();
            
            // test que le remboursement soit correct après impression
            assertEquals("la machine ne rend pas le bon montant après impression d'un ticket", 20, machine.refund());
        }
        
        
        @Test
        // S8 : vérifie que le remboursement remette la balance à zéro
        public void refundSetBalanceToZero() {
            
            //ajout financier
            machine.insertMoney(PRICE);
            
            //demande de remboursement
            machine.refund();
            
            // test que le remboursement ait remis à zéro avant l'impression
            assertEquals("La machine n'a pas remis à 0 le montant avant impression", 0, machine.getBalance());
            
            
            // ajout financier pour ticket + supplement financier
            machine.insertMoney(PRICE);
            machine.insertMoney(20);
            
            //impression d'un ticket
            machine.printTicket();
            
            //demande de remboursement
            machine.refund();
            
            // test que le remboursement soit correct après impression
            assertEquals("la machine n'a pas remis à 0 le montant après impression d'un ticket", 0, machine.getBalance());
        }
        
        
        
        @Test
        // S9 : vérifie que l'on ne puisse pas insérer un montant négatif dans la machine
        public void insertNegativeMoney() {
            
            // tentative d'ajout d'un montant négatif sans montant de base
            machine.insertMoney(-20);
            
            //vérification du montant de la balance
            assertEquals("Le montant négatif a été enregistré avec une base vide", 0, machine.getBalance());
            
            //Ajout d'une somme de base
            machine.insertMoney(PRICE);
            
            //Ajout montant négatif
            machine.insertMoney(-15);
            
            //vérification que le montant n'a pas bougé
            assertEquals("Le montant négatif a été enregistré avec une somme de base", PRICE, machine.getBalance());
        }
        
        
        
        @Test
        // S10 : vérification que l'on ne puisse pas créer de ticket dont le prix est négatif
        public void machineWithNegativeStart() {
            try{
                // tentative de création d'une machine avec un montant négatif
                TicketMachine new_machine = new TicketMachine(-20);
            } catch (IllegalArgumentException e) {
                
                System.out.println("test Ok");
            }
            
        }
        
}
