import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITenant } from 'app/shared/model/tenant.model';
import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './branch.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBranchUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BranchUpdate = (props: IBranchUpdateProps) => {
  const [tenantId, setTenantId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { branchEntity, tenants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/branch' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTenants();
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
        ...branchEntity,
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
          <h2 id="carePlannerApp.branch.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.branch.home.createOrEditLabel">Create or edit a Branch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : branchEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="branch-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="branch-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="branch-name">
                  <Translate contentKey="carePlannerApp.branch.name">Name</Translate>
                </Label>
                <AvField
                  id="branch-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="branch-address">
                  <Translate contentKey="carePlannerApp.branch.address">Address</Translate>
                </Label>
                <AvField id="branch-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="branch-telephone">
                  <Translate contentKey="carePlannerApp.branch.telephone">Telephone</Translate>
                </Label>
                <AvField id="branch-telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="branch-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.branch.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="branch-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.branchEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="branch-tenant">
                  <Translate contentKey="carePlannerApp.branch.tenant">Tenant</Translate>
                </Label>
                <AvInput id="branch-tenant" type="select" className="form-control" name="tenantId">
                  <option value="" key="0" />
                  {tenants
                    ? tenants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.tenantName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/branch" replace color="info">
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
  tenants: storeState.tenant.entities,
  branchEntity: storeState.branch.entity,
  loading: storeState.branch.loading,
  updating: storeState.branch.updating,
  updateSuccess: storeState.branch.updateSuccess,
});

const mapDispatchToProps = {
  getTenants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BranchUpdate);
