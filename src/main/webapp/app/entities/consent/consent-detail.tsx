import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './consent.reducer';
import { IConsent } from 'app/shared/model/consent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsentDetail = (props: IConsentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { consentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.consent.detail.title">Consent</Translate> [<b>{consentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="carePlannerApp.consent.title">Title</Translate>
            </span>
          </dt>
          <dd>{consentEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.consent.description">Description</Translate>
            </span>
          </dt>
          <dd>{consentEntity.description}</dd>
          <dt>
            <span id="giveConsent">
              <Translate contentKey="carePlannerApp.consent.giveConsent">Give Consent</Translate>
            </span>
          </dt>
          <dd>{consentEntity.giveConsent ? 'true' : 'false'}</dd>
          <dt>
            <span id="arrangements">
              <Translate contentKey="carePlannerApp.consent.arrangements">Arrangements</Translate>
            </span>
          </dt>
          <dd>{consentEntity.arrangements}</dd>
          <dt>
            <span id="serviceUserSignature">
              <Translate contentKey="carePlannerApp.consent.serviceUserSignature">Service User Signature</Translate>
            </span>
          </dt>
          <dd>{consentEntity.serviceUserSignature}</dd>
          <dt>
            <span id="signatureImage">
              <Translate contentKey="carePlannerApp.consent.signatureImage">Signature Image</Translate>
            </span>
          </dt>
          <dd>
            {consentEntity.signatureImage ? (
              <div>
                {consentEntity.signatureImageContentType ? (
                  <a onClick={openFile(consentEntity.signatureImageContentType, consentEntity.signatureImage)}>
                    <img
                      src={`data:${consentEntity.signatureImageContentType};base64,${consentEntity.signatureImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {consentEntity.signatureImageContentType}, {byteSize(consentEntity.signatureImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="signatureImageUrl">
              <Translate contentKey="carePlannerApp.consent.signatureImageUrl">Signature Image Url</Translate>
            </span>
          </dt>
          <dd>{consentEntity.signatureImageUrl}</dd>
          <dt>
            <span id="consentDate">
              <Translate contentKey="carePlannerApp.consent.consentDate">Consent Date</Translate>
            </span>
          </dt>
          <dd>
            {consentEntity.consentDate ? <TextFormat value={consentEntity.consentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.consent.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {consentEntity.lastUpdatedDate ? (
              <TextFormat value={consentEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.consent.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{consentEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.consent.serviceUser">Service User</Translate>
          </dt>
          <dd>{consentEntity.serviceUserServiceUserCode ? consentEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.consent.witnessedBy">Witnessed By</Translate>
          </dt>
          <dd>{consentEntity.witnessedByEmployeeCode ? consentEntity.witnessedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/consent" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/consent/${consentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ consent }: IRootState) => ({
  consentEntity: consent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsentDetail);
