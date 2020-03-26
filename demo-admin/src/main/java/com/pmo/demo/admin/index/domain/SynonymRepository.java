package com.pmo.demo.admin.index.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Long> {
}
