package server.services;

import model.Site;
import server.repository.SiteRepository;

import java.util.List;

public class SiteService {
    private SiteRepository siteRepository;

    public SiteService() {
        siteRepository = new SiteRepository();
    }

    public List<Site> selectAll() {
        return siteRepository.selectAll();
    }
}


