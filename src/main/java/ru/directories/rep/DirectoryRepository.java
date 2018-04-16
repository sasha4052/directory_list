package ru.directories.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.directories.dom.Directory;

public interface DirectoryRepository extends JpaRepository<Directory, Integer> {

}

