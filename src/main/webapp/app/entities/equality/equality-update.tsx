import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './equality.reducer';
import { IEquality } from 'app/shared/model/equality.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEqualityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EqualityUpdate = (props: IEqualityUpdateProps) => {
  const [nationalityId, setNationalityId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { equalityEntity, countries, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/equality' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCountries();
    props.getServiceUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...equalityEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carePlannerApp.equality.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.equality.home.createOrEditLabel">Create or edit a Equality</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : equalityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="equality-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="equality-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="genderLabel" for="equality-gender">
                  <Translate contentKey="carePlannerApp.equality.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="equality-gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && equalityEntity.gender) || 'MALE'}
                >
                  <option value="MALE">{translate('carePlannerApp.Gender.MALE')}</option>
                  <option value="FEMALE">{translate('carePlannerApp.Gender.FEMALE')}</option>
                  <option value="OTHER">{translate('carePlannerApp.Gender.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="maritalStatusLabel" for="equality-maritalStatus">
                  <Translate contentKey="carePlannerApp.equality.maritalStatus">Marital Status</Translate>
                </Label>
                <AvInput
                  id="equality-maritalStatus"
                  type="select"
                  className="form-control"
                  name="maritalStatus"
                  value={(!isNew && equalityEntity.maritalStatus) || 'MARRIED'}
                >
                  <option value="MARRIED">{translate('carePlannerApp.MaritalStatus.MARRIED')}</option>
                  <option value="SINGLE">{translate('carePlannerApp.MaritalStatus.SINGLE')}</option>
                  <option value="DIVORCED">{translate('carePlannerApp.MaritalStatus.DIVORCED')}</option>
                  <option value="WIDOWED">{translate('carePlannerApp.MaritalStatus.WIDOWED')}</option>
                  <option value="OTHER">{translate('carePlannerApp.MaritalStatus.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="religionLabel" for="equality-religion">
                  <Translate contentKey="carePlannerApp.equality.religion">Religion</Translate>
                </Label>
                <AvInput
                  id="equality-religion"
                  type="select"
                  className="form-control"
                  name="religion"
                  value={(!isNew && equalityEntity.religion) || 'MUSLIM'}
                >
                  <option value="MUSLIM">{translate('carePlannerApp.Religion.MUSLIM')}</option>
                  <option value="CHRISTIANITY">{translate('carePlannerApp.Religion.CHRISTIANITY')}</option>
                  <option value="HINDU">{translate('carePlannerApp.Religion.HINDU')}</option>
                  <option value="ATHEIST">{translate('carePlannerApp.Religion.ATHEIST')}</option>
                  <option value="JEWISH">{translate('carePlannerApp.Religion.JEWISH')}</option>
                  <option value="OTHER">{translate('carePlannerApp.Religion.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="equality-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.equality.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="equality-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.equalityEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="equality-tenantId">
                  <Translate contentKey="carePlannerApp.equality.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="equality-tenantId"
                  type="string"
                  className="form-control"
                  name="tenantId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="equality-nationality">
                  <Translate contentKey="carePlannerApp.equality.nationality">Nationality</Translate>
                </Label>
                <AvInput id="equality-nationality" type="select" className="form-control" name="nationalityId">
                  <option value="" key="0" />
                  {countries
                    ? countries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.countryName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="equality-serviceUser">
                  <Translate contentKey="carePlannerApp.equality.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="equality-serviceUser" type="select" className="form-control" name="serviceUserId">
                  <option value="" key="0" />
                  {serviceUsers
                    ? serviceUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.serviceUserCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/equality" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  countries: storeState.country.entities,
  serviceUsers: storeState.serviceUser.entities,
  equalityEntity: storeState.equality.entity,
  loading: storeState.equality.loading,
  updating: storeState.equality.updating,
  updateSuccess: storeState.equality.updateSuccess,
});

const mapDispatchToProps = {
  getCountries,
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EqualityUpdate);
