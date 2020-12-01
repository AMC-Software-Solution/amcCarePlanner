import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICurrency } from 'app/shared/model/currency.model';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service-order.reducer';
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceOrderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceOrderUpdate = (props: IServiceOrderUpdateProps) => {
  const [currencyId, setCurrencyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceOrderEntity, currencies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service-order' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCurrencies();
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
        ...serviceOrderEntity,
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
          <h2 id="carePlannerApp.serviceOrder.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.serviceOrder.home.createOrEditLabel">Create or edit a ServiceOrder</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceOrderEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-order-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-order-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="service-order-title">
                  <Translate contentKey="carePlannerApp.serviceOrder.title">Title</Translate>
                </Label>
                <AvField
                  id="service-order-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="serviceDescriptionLabel" for="service-order-serviceDescription">
                  <Translate contentKey="carePlannerApp.serviceOrder.serviceDescription">Service Description</Translate>
                </Label>
                <AvField id="service-order-serviceDescription" type="text" name="serviceDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="serviceHourlyRateLabel" for="service-order-serviceHourlyRate">
                  <Translate contentKey="carePlannerApp.serviceOrder.serviceHourlyRate">Service Hourly Rate</Translate>
                </Label>
                <AvField
                  id="service-order-serviceHourlyRate"
                  type="string"
                  className="form-control"
                  name="serviceHourlyRate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="service-order-clientId">
                  <Translate contentKey="carePlannerApp.serviceOrder.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="service-order-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="service-order-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.serviceOrder.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="service-order-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceOrderEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="service-order-currency">
                  <Translate contentKey="carePlannerApp.serviceOrder.currency">Currency</Translate>
                </Label>
                <AvInput id="service-order-currency" type="select" className="form-control" name="currencyId">
                  <option value="" key="0" />
                  {currencies
                    ? currencies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.currency}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/service-order" replace color="info">
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
  currencies: storeState.currency.entities,
  serviceOrderEntity: storeState.serviceOrder.entity,
  loading: storeState.serviceOrder.loading,
  updating: storeState.serviceOrder.updating,
  updateSuccess: storeState.serviceOrder.updateSuccess,
});

const mapDispatchToProps = {
  getCurrencies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceOrderUpdate);
