package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.MpaDao;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDao mpaDao;

    public Collection<Mpa> getAll() {
        return mpaDao.findAllMpa();
    }

    public Mpa getById(int id) throws MpaNotFoundException {
        return mpaDao.findMpaById(id);
    }
}
