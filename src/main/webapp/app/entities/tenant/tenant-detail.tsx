import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tenant.reducer';
import { ITenant } from 'app/shared/model/tenant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITenantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TenantDetail = (props: ITenantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tenantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.tenant.detail.title">Tenant</Translate> [<b>{tenantEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tenantName">
              <Translate contentKey="carePlannerApp.tenant.tenantName">Tenant Name</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantName}</dd>
          <dt>
            <span id="tenantDescription">
              <Translate contentKey="carePlannerApp.tenant.tenantDescription">Tenant Description</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantDescription}</dd>
          <dt>
            <span id="tenantLogo">
              <Translate contentKey="carePlannerApp.tenant.tenantLogo">Tenant Logo</Translate>
            </span>
          </dt>
          <dd>
            {tenantEntity.tenantLogo ? (
              <div>
                {tenantEntity.tenantLogoContentType ? (
                  <a onClick={openFile(tenantEntity.tenantLogoContentType, tenantEntity.tenantLogo)}>
                    <img
                      src={`data:${tenantEntity.tenantLogoContentType};base64,${tenantEntity.tenantLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {tenantEntity.tenantLogoContentType}, {byteSize(tenantEntity.tenantLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="tenantLogoUrl">
              <Translate contentKey="carePlannerApp.tenant.tenantLogoUrl">Tenant Logo Url</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantLogoUrl}</dd>
          <dt>
            <span id="tenantContactName">
              <Translate contentKey="carePlannerApp.tenant.tenantContactName">Tenant Contact Name</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantContactName}</dd>
          <dt>
            <span id="tenantPhone">
              <Translate contentKey="carePlannerApp.tenant.tenantPhone">Tenant Phone</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantPhone}</dd>
          <dt>
            <span id="tenantEmail">
              <Translate contentKey="carePlannerApp.tenant.tenantEmail">Tenant Email</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.tenantEmail}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.tenant.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{tenantEntity.createdDate ? <TextFormat value={tenantEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.tenant.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {tenantEntity.lastUpdatedDate ? <TextFormat value={tenantEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tenant" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tenant/${tenantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tenant }: IRootState) => ({
  tenantEntity: tenant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TenantDetail);
