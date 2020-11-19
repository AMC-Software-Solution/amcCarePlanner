import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './document-type.reducer';
import { IDocumentType } from 'app/shared/model/document-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDocumentTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DocumentTypeUpdate = (props: IDocumentTypeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { documentTypeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/document-type' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
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
        ...documentTypeEntity,
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
          <h2 id="carePlannerApp.documentType.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.documentType.home.createOrEditLabel">Create or edit a DocumentType</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : documentTypeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="document-type-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="document-type-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="documentTypeTitleLabel" for="document-type-documentTypeTitle">
                  <Translate contentKey="carePlannerApp.documentType.documentTypeTitle">Document Type Title</Translate>
                </Label>
                <AvField
                  id="document-type-documentTypeTitle"
                  type="text"
                  name="documentTypeTitle"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentTypeCodeLabel" for="document-type-documentTypeCode">
                  <Translate contentKey="carePlannerApp.documentType.documentTypeCode">Document Type Code</Translate>
                </Label>
                <AvField
                  id="document-type-documentTypeCode"
                  type="text"
                  name="documentTypeCode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentTypeDescriptionLabel" for="document-type-documentTypeDescription">
                  <Translate contentKey="carePlannerApp.documentType.documentTypeDescription">Document Type Description</Translate>
                </Label>
                <AvField id="document-type-documentTypeDescription" type="text" name="documentTypeDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="document-type-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.documentType.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="document-type-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.documentTypeEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/document-type" replace color="info">
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
  documentTypeEntity: storeState.documentType.entity,
  loading: storeState.documentType.loading,
  updating: storeState.documentType.updating,
  updateSuccess: storeState.documentType.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DocumentTypeUpdate);
