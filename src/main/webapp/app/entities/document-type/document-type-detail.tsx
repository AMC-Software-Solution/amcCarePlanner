import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './document-type.reducer';
import { IDocumentType } from 'app/shared/model/document-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDocumentTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DocumentTypeDetail = (props: IDocumentTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { documentTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.documentType.detail.title">DocumentType</Translate> [<b>{documentTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="documentTypeTitle">
              <Translate contentKey="carePlannerApp.documentType.documentTypeTitle">Document Type Title</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.documentTypeTitle}</dd>
          <dt>
            <span id="documentTypeDescription">
              <Translate contentKey="carePlannerApp.documentType.documentTypeDescription">Document Type Description</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.documentTypeDescription}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.documentType.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeEntity.createdDate ? (
              <TextFormat value={documentTypeEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.documentType.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeEntity.lastUpdatedDate ? (
              <TextFormat value={documentTypeEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.documentType.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/document-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-type/${documentTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ documentType }: IRootState) => ({
  documentTypeEntity: documentType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DocumentTypeDetail);
