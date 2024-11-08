package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}
	@Test
	// S3 : On n’imprime pas le ticket si le montant inséré est insuffisant
	void printTicketFailsIfInsufficientFunds() {
		machine.insertMoney(30);
		assertFalse(machine.printTicket(), "Le ticket a été imprimé alors que le solde était insuffisant");
	}

	@Test
		// S4 : On imprime le ticket si le montant inséré est suffisant
	void printTicketSucceedsIfSufficientFunds() {
		machine.insertMoney(50);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé alors que le solde était suffisant");
	}

	@Test
		// S5 : Quand on imprime un ticket, la balance est décrémentée du prix du ticket
	void balanceIsDecreasedWhenTicketIsPrinted() {
		machine.insertMoney(100);
		machine.printTicket();
		assertEquals(50, machine.getBalance(), "La balance n'a pas été correctement mise à jour après l'impression du ticket");
	}

	@Test
		// S6 : Le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalCollectedIsUpdatedWhenTicketIsPrinted() {
		machine.insertMoney(100);
		machine.printTicket();
		assertEquals(50, machine.getTotal(), "Le montant total collecté n'a pas été correctement mis à jour après l'impression du ticket");
	}

	@Test
		// S7 : Refund() rend correctement la monnaie
	void refundReturnsCorrectAmount() {
		machine.insertMoney(100);
		int refundedAmount = machine.refund();
		assertEquals(100, refundedAmount, "Le remboursement n'est pas correct");
	}

	@Test
		// S8 : Refund() remet la balance à zéro
	void refundResetsBalance() {
		machine.insertMoney(100);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été réinitialisée après le remboursement");
	}

	@Test
		// S9 : On ne peut pas insérer un montant négatif
	void insertNegativeMoneyThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "La machine a accepté un montant négatif");
	}

	@Test
		// S10 : On ne peut pas créer de machine avec un prix négatif
	void createMachineWithNegativePriceThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-50), "La machine a été créée avec un prix négatif");
	}
}
