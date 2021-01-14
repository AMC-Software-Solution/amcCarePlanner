import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service-user-contact.reducer';
import { IServiceUserContact } from 'app/shared/model/service-user-contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceUserContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserContactUpdate = (props: IServiceUserContactUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceUserContactEntity, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service-user-contact' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...serviceUserContactEntity,
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
          <h2 id="carePlannerApp.serviceUserContact.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.serviceUserContact.home.createOrEditLabel">Create or edit a ServiceUserContact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceUserContactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-user-contact-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-user-contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="addressLabel" for="service-user-contact-address">
                  <Translate contentKey="carePlannerApp.serviceUserContact.address">Address</Translate>
                </Label>
                <AvField
                  id="service-user-contact-address"
                  type="text"
                  name="address"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cityOrTownLabel" for="service-user-contact-cityOrTown">
                  <Translate contentKey="carePlannerApp.serviceUserContact.cityOrTown">City Or Town</Translate>
                </Label>
                <AvField
                  id="service-user-contact-cityOrTown"
                  type="text"
                  name="cityOrTown"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countyLabel" for="service-user-contact-county">
                  <Translate contentKey="carePlannerApp.serviceUserContact.county">County</Translate>
                </Label>
                <AvField id="service-user-contact-county" type="text" name="county" />
              </AvGroup>
              <AvGroup>
                <Label id="postCodeLabel" for="service-user-contact-postCode">
                  <Translate contentKey="carePlannerApp.serviceUserContact.postCode">Post Code</Translate>
                </Label>
                <AvField
                  id="service-user-contact-postCode"
                  type="text"
                  name="postCode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="service-user-contact-telephone">
                  <Translate contentKey="carePlannerApp.serviceUserContact.telephone">Telephone</Translate>
                </Label>
                <AvField
                  id="service-user-contact-telephone"
                  type="text"
                  name="telephone"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="service-user-contact-createdDate">
                  <Translate contentKey="carePlannerApp.serviceUserContact.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="service-user-contact-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserContactEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="service-user-contact-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.serviceUserContact.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="service-user-contact-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserContactEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="service-user-contact-clientId">
                  <Translate contentKey="carePlannerApp.serviceUserContact.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="service-user-contact-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="service-user-contact-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.serviceUserContact.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="service-user-contact-serviceUser">
                  <Translate contentKey="carePlannerApp.serviceUserContact.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="service-user-contact-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/service-user-contact" replace color="info">
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
  serviceUsers: storeState.serviceUser.entities,
  serviceUserContactEntity: storeState.serviceUserContact.entity,
  loading: storeState.serviceUserContact.loading,
  updating: storeState.serviceUserContact.updating,
  updateSuccess: storeState.serviceUserContact.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserContactUpdate);
