package javapuffs.journeo;

import org.springframework.stereotype.Repository;

@Repository
public class CountryRepo {

    ICountryRepo repo;

    public CountryRepo(ICountryRepo repo) {
        this.repo = repo;
    }
}
