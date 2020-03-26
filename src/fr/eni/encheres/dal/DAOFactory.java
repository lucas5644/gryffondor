package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;

public class DAOFactory {
	//Instance d'EnchereDAO
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}

}
