import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './tenant.reducer';
import { ITenant } from 'app/shared/model/tenant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ITenantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Tenant = (props: ITenantProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { tenantList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="tenant-heading">
        <Translate contentKey="carePlannerApp.tenant.home.title">Tenants</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.tenant.home.createLabel">Create new Tenant</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {tenantList && tenantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantName')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantName">Tenant Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantDescription')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantDescription">Tenant Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantLogo')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantLogo">Tenant Logo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantLogoUrl')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantLogoUrl">Tenant Logo Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantContactName')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantContactName">Tenant Contact Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantPhone')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantPhone">Tenant Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantEmail')}>
                  <Translate contentKey="carePlannerApp.tenant.tenantEmail">Tenant Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.tenant.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.tenant.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tenantList.map((tenant, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${tenant.id}`} color="link" size="sm">
                      {tenant.id}
                    </Button>
                  </td>
                  <td>{tenant.tenantName}</td>
                  <td>{tenant.tenantDescription}</td>
                  <td>
                    {tenant.tenantLogo ? (
                      <div>
                        {tenant.tenantLogoContentType ? (
                          <a onClick={openFile(tenant.tenantLogoContentType, tenant.tenantLogo)}>
                            <img src={`data:${tenant.tenantLogoContentType};base64,${tenant.tenantLogo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {tenant.tenantLogoContentType}, {byteSize(tenant.tenantLogo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{tenant.tenantLogoUrl}</td>
                  <td>{tenant.tenantContactName}</td>
                  <td>{tenant.tenantPhone}</td>
                  <td>{tenant.tenantEmail}</td>
                  <td>{tenant.createdDate ? <TextFormat type="date" value={tenant.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {tenant.lastUpdatedDate ? <TextFormat type="date" value={tenant.lastUpdatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tenant.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${tenant.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${tenant.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.tenant.home.notFound">No Tenants found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={tenantList && tenantList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ tenant }: IRootState) => ({
  tenantList: tenant.entities,
  loading: tenant.loading,
  totalItems: tenant.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Tenant);
