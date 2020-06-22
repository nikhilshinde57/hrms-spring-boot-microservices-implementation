package com.niks.oauth2authenticationservice.repository;

import com.niks.oauth2authenticationservice.models.ERole;
import com.niks.oauth2authenticationservice.models.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleRepositoryImpl implements RoleRepository {

  @Override
  public Optional<Role> findByName(ERole name) {
    return Optional.empty();
  }

  @Override
  public List<Role> findAll() {
    return null;
  }

  @Override
  public List<Role> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Role> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Role> findAllById(Iterable<Long> iterable) {
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
  public void delete(Role role) {

  }

  @Override
  public void deleteAll(Iterable<? extends Role> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Role> S save(S s) {
    return null;
  }

  @Override
  public <S extends Role> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Role> findById(Long aLong) {
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
  public <S extends Role> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Role> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Role getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Role> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Role> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Role> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Role> boolean exists(Example<S> example) {
    return false;
  }
}
