import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITenantDocument, defaultValue } from 'app/shared/model/tenant-document.model';

export const ACTION_TYPES = {
  FETCH_TENANTDOCUMENT_LIST: 'tenantDocument/FETCH_TENANTDOCUMENT_LIST',
  FETCH_TENANTDOCUMENT: 'tenantDocument/FETCH_TENANTDOCUMENT',
  CREATE_TENANTDOCUMENT: 'tenantDocument/CREATE_TENANTDOCUMENT',
  UPDATE_TENANTDOCUMENT: 'tenantDocument/UPDATE_TENANTDOCUMENT',
  DELETE_TENANTDOCUMENT: 'tenantDocument/DELETE_TENANTDOCUMENT',
  SET_BLOB: 'tenantDocument/SET_BLOB',
  RESET: 'tenantDocument/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITenantDocument>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TenantDocumentState = Readonly<typeof initialState>;

// Reducer

export default (state: TenantDocumentState = initialState, action): TenantDocumentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TENANTDOCUMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TENANTDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TENANTDOCUMENT):
    case REQUEST(ACTION_TYPES.UPDATE_TENANTDOCUMENT):
    case REQUEST(ACTION_TYPES.DELETE_TENANTDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TENANTDOCUMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TENANTDOCUMENT):
    case FAILURE(ACTION_TYPES.CREATE_TENANTDOCUMENT):
    case FAILURE(ACTION_TYPES.UPDATE_TENANTDOCUMENT):
    case FAILURE(ACTION_TYPES.DELETE_TENANTDOCUMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TENANTDOCUMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TENANTDOCUMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TENANTDOCUMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_TENANTDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TENANTDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/tenant-documents';

// Actions

export const getEntities: ICrudGetAllAction<ITenantDocument> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TENANTDOCUMENT_LIST,
    payload: axios.get<ITenantDocument>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITenantDocument> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TENANTDOCUMENT,
    payload: axios.get<ITenantDocument>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITenantDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TENANTDOCUMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITenantDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TENANTDOCUMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITenantDocument> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TENANTDOCUMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
