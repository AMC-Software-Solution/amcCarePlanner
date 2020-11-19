import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceOrder } from 'app/shared/model/service-order.model';
import { getEntities as getServiceOrders } from 'app/entities/service-order/service-order.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInvoiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceUpdate = (props: IInvoiceUpdateProps) => {
  const [serviceOrderId, setServiceOrderId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { invoiceEntity, serviceOrders, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/invoice' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceOrders();
    props.getServiceUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.generatedDate = convertDateTimeToServer(values.generatedDate);
    values.dueDate = convertDateTimeToServer(values.dueDate);
    values.paymentDate = convertDateTimeToServer(values.paymentDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...invoiceEntity,
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
          <h2 id="carePlannerApp.invoice.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.invoice.home.createOrEditLabel">Create or edit a Invoice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : invoiceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="invoice-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="invoice-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="totalAmountLabel" for="invoice-totalAmount">
                  <Translate contentKey="carePlannerApp.invoice.totalAmount">Total Amount</Translate>
                </Label>
                <AvField
                  id="invoice-totalAmount"
                  type="text"
                  name="totalAmount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="invoice-description">
                  <Translate contentKey="carePlannerApp.invoice.description">Description</Translate>
                </Label>
                <AvField id="invoice-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="invoiceNumberLabel" for="invoice-invoiceNumber">
                  <Translate contentKey="carePlannerApp.invoice.invoiceNumber">Invoice Number</Translate>
                </Label>
                <AvField
                  id="invoice-invoiceNumber"
                  type="text"
                  name="invoiceNumber"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="generatedDateLabel" for="invoice-generatedDate">
                  <Translate contentKey="carePlannerApp.invoice.generatedDate">Generated Date</Translate>
                </Label>
                <AvInput
                  id="invoice-generatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="generatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.invoiceEntity.generatedDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dueDateLabel" for="invoice-dueDate">
                  <Translate contentKey="carePlannerApp.invoice.dueDate">Due Date</Translate>
                </Label>
                <AvInput
                  id="invoice-dueDate"
                  type="datetime-local"
                  className="form-control"
                  name="dueDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.invoiceEntity.dueDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="paymentDateLabel" for="invoice-paymentDate">
                  <Translate contentKey="carePlannerApp.invoice.paymentDate">Payment Date</Translate>
                </Label>
                <AvInput
                  id="invoice-paymentDate"
                  type="datetime-local"
                  className="form-control"
                  name="paymentDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.invoiceEntity.paymentDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="invoiceStatusLabel" for="invoice-invoiceStatus">
                  <Translate contentKey="carePlannerApp.invoice.invoiceStatus">Invoice Status</Translate>
                </Label>
                <AvInput
                  id="invoice-invoiceStatus"
                  type="select"
                  className="form-control"
                  name="invoiceStatus"
                  value={(!isNew && invoiceEntity.invoiceStatus) || 'CREATED'}
                >
                  <option value="CREATED">{translate('carePlannerApp.InvoiceStatus.CREATED')}</option>
                  <option value="PAID">{translate('carePlannerApp.InvoiceStatus.PAID')}</option>
                  <option value="CANCELLED">{translate('carePlannerApp.InvoiceStatus.CANCELLED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="taxLabel" for="invoice-tax">
                  <Translate contentKey="carePlannerApp.invoice.tax">Tax</Translate>
                </Label>
                <AvField id="invoice-tax" type="string" className="form-control" name="tax" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute1Label" for="invoice-attribute1">
                  <Translate contentKey="carePlannerApp.invoice.attribute1">Attribute 1</Translate>
                </Label>
                <AvField id="invoice-attribute1" type="text" name="attribute1" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute2Label" for="invoice-attribute2">
                  <Translate contentKey="carePlannerApp.invoice.attribute2">Attribute 2</Translate>
                </Label>
                <AvField id="invoice-attribute2" type="text" name="attribute2" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute3Label" for="invoice-attribute3">
                  <Translate contentKey="carePlannerApp.invoice.attribute3">Attribute 3</Translate>
                </Label>
                <AvField id="invoice-attribute3" type="text" name="attribute3" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute4Label" for="invoice-attribute4">
                  <Translate contentKey="carePlannerApp.invoice.attribute4">Attribute 4</Translate>
                </Label>
                <AvField id="invoice-attribute4" type="text" name="attribute4" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute5Label" for="invoice-attribute5">
                  <Translate contentKey="carePlannerApp.invoice.attribute5">Attribute 5</Translate>
                </Label>
                <AvField id="invoice-attribute5" type="text" name="attribute5" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute6Label" for="invoice-attribute6">
                  <Translate contentKey="carePlannerApp.invoice.attribute6">Attribute 6</Translate>
                </Label>
                <AvField id="invoice-attribute6" type="text" name="attribute6" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute7Label" for="invoice-attribute7">
                  <Translate contentKey="carePlannerApp.invoice.attribute7">Attribute 7</Translate>
                </Label>
                <AvField id="invoice-attribute7" type="text" name="attribute7" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="invoice-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.invoice.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="invoice-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.invoiceEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="invoice-tenantId">
                  <Translate contentKey="carePlannerApp.invoice.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="invoice-tenantId"
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
                <Label for="invoice-serviceOrder">
                  <Translate contentKey="carePlannerApp.invoice.serviceOrder">Service Order</Translate>
                </Label>
                <AvInput id="invoice-serviceOrder" type="select" className="form-control" name="serviceOrderId">
                  <option value="" key="0" />
                  {serviceOrders
                    ? serviceOrders.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.title}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="invoice-serviceUser">
                  <Translate contentKey="carePlannerApp.invoice.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="invoice-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/invoice" replace color="info">
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
  serviceOrders: storeState.serviceOrder.entities,
  serviceUsers: storeState.serviceUser.entities,
  invoiceEntity: storeState.invoice.entity,
  loading: storeState.invoice.loading,
  updating: storeState.invoice.updating,
  updateSuccess: storeState.invoice.updateSuccess,
});

const mapDispatchToProps = {
  getServiceOrders,
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceUpdate);
