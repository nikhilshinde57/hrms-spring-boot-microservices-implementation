package com.niks.oauth2authenticationservice.repository;

import com.niks.oauth2authenticationservice.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.empty();
  }

  @Override
  public Boolean existsByUsername(String username) {
    return null;
  }

  @Override
  public Boolean existsByEmail(String email) {
    return null;
  }

  @Override
  public List<User> findAll() {
    return null;
  }

  @Override
  public List<User> findAllById(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(User user) {

  }

  @Override
  public void deleteAll(Iterable<? extends User> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends User> S save(S s) {
    return null;
  }

  @Override
  public <S extends User> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<User> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends User> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<User> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public User getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends User> List<S> findAll(org.springframework.data.domain.Example<S> example,
      org.springframework.data.domain.Sort sort) {
    return null;
  }

  @Override
  public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends User> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends User> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends User> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends User> List<S> findAll(org.springframework.data.domain.Example<S> example) {
    return null;
  }

  @Override
  public List<User> findAll(org.springframework.data.domain.Sort sort) {
    return null;
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return null;
  }
}
