package br.com.zupacademy.marcio.ecommerce.compartilhado;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

        @PersistenceContext
        private EntityManager manager;

        private Class<?> klass;

        @Override
        public void initialize(ExistsId annotation) {
            this.klass = annotation.klass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            try {
                Query query = manager.createQuery("select 1 from "+klass.getSimpleName()+" where id = :id");
                query.setParameter("id", value);
                query.getSingleResult();
                return true;
            } catch (NoResultException e) {
                return false;
            }
        }
    }