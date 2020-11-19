import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './tenant.reducer';
import { ITenant } from 'app/shared/model/tenant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITenantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TenantUpdate = (props: ITenantUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { tenantEntity, loading, updating } = props;

  const { tenantLogo, tenantLogoContentType } = tenantEntity;

  const handleClose = () => {
    props.history.push('/tenant' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

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
        ...tenantEntity,
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
          <h2 id="carePlannerApp.tenant.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.tenant.home.createOrEditLabel">Create or edit a Tenant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tenantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="tenant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="tenant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tenantNameLabel" for="tenant-tenantName">
                  <Translate contentKey="carePlannerApp.tenant.tenantName">Tenant Name</Translate>
                </Label>
                <AvField
                  id="tenant-tenantName"
                  type="text"
                  name="tenantName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantDescriptionLabel" for="tenant-tenantDescription">
                  <Translate contentKey="carePlannerApp.tenant.tenantDescription">Tenant Description</Translate>
                </Label>
                <AvField id="tenant-tenantDescription" type="text" name="tenantDescription" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="tenantLogoLabel" for="tenantLogo">
                    <Translate contentKey="carePlannerApp.tenant.tenantLogo">Tenant Logo</Translate>
                  </Label>
                  <br />
                  {tenantLogo ? (
                    <div>
                      {tenantLogoContentType ? (
                        <a onClick={openFile(tenantLogoContentType, tenantLogo)}>
                          <img src={`data:${tenantLogoContentType};base64,${tenantLogo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {tenantLogoContentType}, {byteSize(tenantLogo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('tenantLogo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_tenantLogo" type="file" onChange={onBlobChange(true, 'tenantLogo')} accept="image/*" />
                  <AvInput type="hidden" name="tenantLogo" value={tenantLogo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="tenantLogoUrlLabel" for="tenant-tenantLogoUrl">
                  <Translate contentKey="carePlannerApp.tenant.tenantLogoUrl">Tenant Logo Url</Translate>
                </Label>
                <AvField id="tenant-tenantLogoUrl" type="text" name="tenantLogoUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="tenantContactNameLabel" for="tenant-tenantContactName">
                  <Translate contentKey="carePlannerApp.tenant.tenantContactName">Tenant Contact Name</Translate>
                </Label>
                <AvField id="tenant-tenantContactName" type="text" name="tenantContactName" />
              </AvGroup>
              <AvGroup>
                <Label id="tenantPhoneLabel" for="tenant-tenantPhone">
                  <Translate contentKey="carePlannerApp.tenant.tenantPhone">Tenant Phone</Translate>
                </Label>
                <AvField id="tenant-tenantPhone" type="text" name="tenantPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="tenantEmailLabel" for="tenant-tenantEmail">
                  <Translate contentKey="carePlannerApp.tenant.tenantEmail">Tenant Email</Translate>
                </Label>
                <AvField id="tenant-tenantEmail" type="text" name="tenantEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="tenant-createdDate">
                  <Translate contentKey="carePlannerApp.tenant.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="tenant-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.tenantEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="tenant-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.tenant.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="tenant-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.tenantEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/tenant" replace color="info">
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
  tenantEntity: storeState.tenant.entity,
  loading: storeState.tenant.loading,
  updating: storeState.tenant.updating,
  updateSuccess: storeState.tenant.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TenantUpdate);
