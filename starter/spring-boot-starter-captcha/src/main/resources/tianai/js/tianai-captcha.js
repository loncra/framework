function me(t, e) {
  return function() {
    return t.apply(e, arguments);
  };
}
const { toString: ke } = Object.prototype, { getPrototypeOf: Q } = Object, M = ((t) => (e) => {
  const n = ke.call(e);
  return t[n] || (t[n] = n.slice(8, -1).toLowerCase());
})(/* @__PURE__ */ Object.create(null)), S = (t) => (t = t.toLowerCase(), (e) => M(e) === t), H = (t) => (e) => typeof e === t, { isArray: R } = Array, x = H("undefined");
function Ue(t) {
  return t !== null && !x(t) && t.constructor !== null && !x(t.constructor) && w(t.constructor.isBuffer) && t.constructor.isBuffer(t);
}
const ge = S("ArrayBuffer");
function Fe(t) {
  let e;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? e = ArrayBuffer.isView(t) : e = t && t.buffer && ge(t.buffer), e;
}
const Me = H("string"), w = H("function"), ye = H("number"), j = (t) => t !== null && typeof t == "object", He = (t) => t === !0 || t === !1, B = (t) => {
  if (M(t) !== "object")
    return !1;
  const e = Q(t);
  return (e === null || e === Object.prototype || Object.getPrototypeOf(e) === null) && !(Symbol.toStringTag in t) && !(Symbol.iterator in t);
}, je = S("Date"), Ve = S("File"), qe = S("Blob"), Xe = S("FileList"), Ge = (t) => j(t) && w(t.pipe), ze = (t) => {
  let e;
  return t && (typeof FormData == "function" && t instanceof FormData || w(t.append) && ((e = M(t)) === "formdata" || // detect form-data instance
      e === "object" && w(t.toString) && t.toString() === "[object FormData]"));
}, We = S("URLSearchParams"), $e = (t) => t.trim ? t.trim() : t.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "");
function L(t, e, { allOwnKeys: n = !1 } = {}) {
  if (t === null || typeof t > "u")
    return;
  let i, r;
  if (typeof t != "object" && (t = [t]), R(t))
    for (i = 0, r = t.length; i < r; i++)
      e.call(null, t[i], i, t);
  else {
    const s = n ? Object.getOwnPropertyNames(t) : Object.keys(t), o = s.length;
    let c;
    for (i = 0; i < o; i++)
      c = s[i], e.call(null, t[c], c, t);
  }
}
function Ee(t, e) {
  e = e.toLowerCase();
  const n = Object.keys(t);
  let i = n.length, r;
  for (; i-- > 0; )
    if (r = n[i], e === r.toLowerCase())
      return r;
  return null;
}
const we = (() => typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : typeof window < "u" ? window : global)(), be = (t) => !x(t) && t !== we;
function $() {
  const { caseless: t } = be(this) && this || {}, e = {}, n = (i, r) => {
    const s = t && Ee(e, r) || r;
    B(e[s]) && B(i) ? e[s] = $(e[s], i) : B(i) ? e[s] = $({}, i) : R(i) ? e[s] = i.slice() : e[s] = i;
  };
  for (let i = 0, r = arguments.length; i < r; i++)
    arguments[i] && L(arguments[i], n);
  return e;
}
const Je = (t, e, n, { allOwnKeys: i } = {}) => (L(e, (r, s) => {
  n && w(r) ? t[s] = me(r, n) : t[s] = r;
}, { allOwnKeys: i }), t), Ye = (t) => (t.charCodeAt(0) === 65279 && (t = t.slice(1)), t), Ke = (t, e, n, i) => {
  t.prototype = Object.create(e.prototype, i), t.prototype.constructor = t, Object.defineProperty(t, "super", {
    value: e.prototype
  }), n && Object.assign(t.prototype, n);
}, Qe = (t, e, n, i) => {
  let r, s, o;
  const c = {};
  if (e = e || {}, t == null)
    return e;
  do {
    for (r = Object.getOwnPropertyNames(t), s = r.length; s-- > 0; )
      o = r[s], (!i || i(o, t, e)) && !c[o] && (e[o] = t[o], c[o] = !0);
    t = n !== !1 && Q(t);
  } while (t && (!n || n(t, e)) && t !== Object.prototype);
  return e;
}, Ze = (t, e, n) => {
  t = String(t), (n === void 0 || n > t.length) && (n = t.length), n -= e.length;
  const i = t.indexOf(e, n);
  return i !== -1 && i === n;
}, et = (t) => {
  if (!t)
    return null;
  if (R(t))
    return t;
  let e = t.length;
  if (!ye(e))
    return null;
  const n = new Array(e);
  for (; e-- > 0; )
    n[e] = t[e];
  return n;
}, tt = ((t) => (e) => t && e instanceof t)(typeof Uint8Array < "u" && Q(Uint8Array)), nt = (t, e) => {
  const i = (t && t[Symbol.iterator]).call(t);
  let r;
  for (; (r = i.next()) && !r.done; ) {
    const s = r.value;
    e.call(t, s[0], s[1]);
  }
}, it = (t, e) => {
  let n;
  const i = [];
  for (; (n = t.exec(e)) !== null; )
    i.push(n);
  return i;
}, rt = S("HTMLFormElement"), st = (t) => t.toLowerCase().replace(
    /[-_\s]([a-z\d])(\w*)/g,
    function(n, i, r) {
      return i.toUpperCase() + r;
    }
), re = (({ hasOwnProperty: t }) => (e, n) => t.call(e, n))(Object.prototype), ot = S("RegExp"), Te = (t, e) => {
  const n = Object.getOwnPropertyDescriptors(t), i = {};
  L(n, (r, s) => {
    let o;
    (o = e(r, s, t)) !== !1 && (i[s] = o || r);
  }), Object.defineProperties(t, i);
}, at = (t) => {
  Te(t, (e, n) => {
    if (w(t) && ["arguments", "caller", "callee"].indexOf(n) !== -1)
      return !1;
    const i = t[n];
    if (w(i)) {
      if (e.enumerable = !1, "writable" in e) {
        e.writable = !1;
        return;
      }
      e.set || (e.set = () => {
        throw Error("Can not rewrite read-only method '" + n + "'");
      });
    }
  });
}, ct = (t, e) => {
  const n = {}, i = (r) => {
    r.forEach((s) => {
      n[s] = !0;
    });
  };
  return R(t) ? i(t) : i(String(t).split(e)), n;
}, lt = () => {
}, ut = (t, e) => (t = +t, Number.isFinite(t) ? t : e), X = "abcdefghijklmnopqrstuvwxyz", se = "0123456789", Se = {
  DIGIT: se,
  ALPHA: X,
  ALPHA_DIGIT: X + X.toUpperCase() + se
}, dt = (t = 16, e = Se.ALPHA_DIGIT) => {
  let n = "";
  const { length: i } = e;
  for (; t--; )
    n += e[Math.random() * i | 0];
  return n;
};
function ft(t) {
  return !!(t && w(t.append) && t[Symbol.toStringTag] === "FormData" && t[Symbol.iterator]);
}
const ht = (t) => {
  const e = new Array(10), n = (i, r) => {
    if (j(i)) {
      if (e.indexOf(i) >= 0)
        return;
      if (!("toJSON" in i)) {
        e[r] = i;
        const s = R(i) ? [] : {};
        return L(i, (o, c) => {
          const f = n(o, r + 1);
          !x(f) && (s[c] = f);
        }), e[r] = void 0, s;
      }
    }
    return i;
  };
  return n(t, 0);
}, pt = S("AsyncFunction"), mt = (t) => t && (j(t) || w(t)) && w(t.then) && w(t.catch), a = {
  isArray: R,
  isArrayBuffer: ge,
  isBuffer: Ue,
  isFormData: ze,
  isArrayBufferView: Fe,
  isString: Me,
  isNumber: ye,
  isBoolean: He,
  isObject: j,
  isPlainObject: B,
  isUndefined: x,
  isDate: je,
  isFile: Ve,
  isBlob: qe,
  isRegExp: ot,
  isFunction: w,
  isStream: Ge,
  isURLSearchParams: We,
  isTypedArray: tt,
  isFileList: Xe,
  forEach: L,
  merge: $,
  extend: Je,
  trim: $e,
  stripBOM: Ye,
  inherits: Ke,
  toFlatObject: Qe,
  kindOf: M,
  kindOfTest: S,
  endsWith: Ze,
  toArray: et,
  forEachEntry: nt,
  matchAll: it,
  isHTMLForm: rt,
  hasOwnProperty: re,
  hasOwnProp: re,
  // an alias to avoid ESLint no-prototype-builtins detection
  reduceDescriptors: Te,
  freezeMethods: at,
  toObjectSet: ct,
  toCamelCase: st,
  noop: lt,
  toFiniteNumber: ut,
  findKey: Ee,
  global: we,
  isContextDefined: be,
  ALPHABET: Se,
  generateString: dt,
  isSpecCompliantForm: ft,
  toJSONObject: ht,
  isAsyncFn: pt,
  isThenable: mt
};
function m(t, e, n, i, r) {
  Error.call(this), Error.captureStackTrace ? Error.captureStackTrace(this, this.constructor) : this.stack = new Error().stack, this.message = t, this.name = "AxiosError", e && (this.code = e), n && (this.config = n), i && (this.request = i), r && (this.response = r);
}
a.inherits(m, Error, {
  toJSON: function() {
    return {
      // Standard
      message: this.message,
      name: this.name,
      // Microsoft
      description: this.description,
      number: this.number,
      // Mozilla
      fileName: this.fileName,
      lineNumber: this.lineNumber,
      columnNumber: this.columnNumber,
      stack: this.stack,
      // Axios
      config: a.toJSONObject(this.config),
      code: this.code,
      status: this.response && this.response.status ? this.response.status : null
    };
  }
});
const Pe = m.prototype, _e = {};
[
  "ERR_BAD_OPTION_VALUE",
  "ERR_BAD_OPTION",
  "ECONNABORTED",
  "ETIMEDOUT",
  "ERR_NETWORK",
  "ERR_FR_TOO_MANY_REDIRECTS",
  "ERR_DEPRECATED",
  "ERR_BAD_RESPONSE",
  "ERR_BAD_REQUEST",
  "ERR_CANCELED",
  "ERR_NOT_SUPPORT",
  "ERR_INVALID_URL"
  // eslint-disable-next-line func-names
].forEach((t) => {
  _e[t] = { value: t };
});
Object.defineProperties(m, _e);
Object.defineProperty(Pe, "isAxiosError", { value: !0 });
m.from = (t, e, n, i, r, s) => {
  const o = Object.create(Pe);
  return a.toFlatObject(t, o, function(f) {
    return f !== Error.prototype;
  }, (c) => c !== "isAxiosError"), m.call(o, t.message, e, n, i, r), o.cause = t, o.name = t.name, s && Object.assign(o, s), o;
};
const gt = null;
function J(t) {
  return a.isPlainObject(t) || a.isArray(t);
}
function ve(t) {
  return a.endsWith(t, "[]") ? t.slice(0, -2) : t;
}
function oe(t, e, n) {
  return t ? t.concat(e).map(function(r, s) {
    return r = ve(r), !n && s ? "[" + r + "]" : r;
  }).join(n ? "." : "") : e;
}
function yt(t) {
  return a.isArray(t) && !t.some(J);
}
const Et = a.toFlatObject(a, {}, null, function(e) {
  return /^is[A-Z]/.test(e);
});
function V(t, e, n) {
  if (!a.isObject(t))
    throw new TypeError("target must be an object");
  e = e || new FormData(), n = a.toFlatObject(n, {
    metaTokens: !0,
    dots: !1,
    indexes: !1
  }, !1, function(p, P) {
    return !a.isUndefined(P[p]);
  });
  const i = n.metaTokens, r = n.visitor || u, s = n.dots, o = n.indexes, f = (n.Blob || typeof Blob < "u" && Blob) && a.isSpecCompliantForm(e);
  if (!a.isFunction(r))
    throw new TypeError("visitor must be a function");
  function l(h) {
    if (h === null)
      return "";
    if (a.isDate(h))
      return h.toISOString();
    if (!f && a.isBlob(h))
      throw new m("Blob is not supported. Use a Buffer instead.");
    return a.isArrayBuffer(h) || a.isTypedArray(h) ? f && typeof Blob == "function" ? new Blob([h]) : Buffer.from(h) : h;
  }
  function u(h, p, P) {
    let b = h;
    if (h && !P && typeof h == "object") {
      if (a.endsWith(p, "{}"))
        p = i ? p : p.slice(0, -2), h = JSON.stringify(h);
      else if (a.isArray(h) && yt(h) || (a.isFileList(h) || a.endsWith(p, "[]")) && (b = a.toArray(h)))
        return p = ve(p), b.forEach(function(I, De) {
          !(a.isUndefined(I) || I === null) && e.append(
              // eslint-disable-next-line no-nested-ternary
              o === !0 ? oe([p], De, s) : o === null ? p : p + "[]",
              l(I)
          );
        }), !1;
    }
    return J(h) ? !0 : (e.append(oe(P, p, s), l(h)), !1);
  }
  const d = [], g = Object.assign(Et, {
    defaultVisitor: u,
    convertValue: l,
    isVisitable: J
  });
  function y(h, p) {
    if (!a.isUndefined(h)) {
      if (d.indexOf(h) !== -1)
        throw Error("Circular reference detected in " + p.join("."));
      d.push(h), a.forEach(h, function(b, A) {
        (!(a.isUndefined(b) || b === null) && r.call(
            e,
            b,
            a.isString(A) ? A.trim() : A,
            p,
            g
        )) === !0 && y(b, p ? p.concat(A) : [A]);
      }), d.pop();
    }
  }
  if (!a.isObject(t))
    throw new TypeError("data must be an object");
  return y(t), e;
}
function ae(t) {
  const e = {
    "!": "%21",
    "'": "%27",
    "(": "%28",
    ")": "%29",
    "~": "%7E",
    "%20": "+",
    "%00": "\0"
  };
  return encodeURIComponent(t).replace(/[!'()~]|%20|%00/g, function(i) {
    return e[i];
  });
}
function Z(t, e) {
  this._pairs = [], t && V(t, this, e);
}
const Ae = Z.prototype;
Ae.append = function(e, n) {
  this._pairs.push([e, n]);
};
Ae.toString = function(e) {
  const n = e ? function(i) {
    return e.call(this, i, ae);
  } : ae;
  return this._pairs.map(function(r) {
    return n(r[0]) + "=" + n(r[1]);
  }, "").join("&");
};
function wt(t) {
  return encodeURIComponent(t).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
function Oe(t, e, n) {
  if (!e)
    return t;
  const i = n && n.encode || wt, r = n && n.serialize;
  let s;
  if (r ? s = r(e, n) : s = a.isURLSearchParams(e) ? e.toString() : new Z(e, n).toString(i), s) {
    const o = t.indexOf("#");
    o !== -1 && (t = t.slice(0, o)), t += (t.indexOf("?") === -1 ? "?" : "&") + s;
  }
  return t;
}
class bt {
  constructor() {
    this.handlers = [];
  }
  /**
   * Add a new interceptor to the stack
   *
   * @param {Function} fulfilled The function to handle `then` for a `Promise`
   * @param {Function} rejected The function to handle `reject` for a `Promise`
   *
   * @return {Number} An ID used to remove interceptor later
   */
  use(e, n, i) {
    return this.handlers.push({
      fulfilled: e,
      rejected: n,
      synchronous: i ? i.synchronous : !1,
      runWhen: i ? i.runWhen : null
    }), this.handlers.length - 1;
  }
  /**
   * Remove an interceptor from the stack
   *
   * @param {Number} id The ID that was returned by `use`
   *
   * @returns {Boolean} `true` if the interceptor was removed, `false` otherwise
   */
  eject(e) {
    this.handlers[e] && (this.handlers[e] = null);
  }
  /**
   * Clear all interceptors from the stack
   *
   * @returns {void}
   */
  clear() {
    this.handlers && (this.handlers = []);
  }
  /**
   * Iterate over all the registered interceptors
   *
   * This method is particularly useful for skipping over any
   * interceptors that may have become `null` calling `eject`.
   *
   * @param {Function} fn The function to call for each interceptor
   *
   * @returns {void}
   */
  forEach(e) {
    a.forEach(this.handlers, function(i) {
      i !== null && e(i);
    });
  }
}
const ce = bt, Re = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, Tt = typeof URLSearchParams < "u" ? URLSearchParams : Z, St = typeof FormData < "u" ? FormData : null, Pt = typeof Blob < "u" ? Blob : null, _t = (() => {
  let t;
  return typeof navigator < "u" && ((t = navigator.product) === "ReactNative" || t === "NativeScript" || t === "NS") ? !1 : typeof window < "u" && typeof document < "u";
})(), vt = (() => typeof WorkerGlobalScope < "u" && // eslint-disable-next-line no-undef
    self instanceof WorkerGlobalScope && typeof self.importScripts == "function")(), T = {
  isBrowser: !0,
  classes: {
    URLSearchParams: Tt,
    FormData: St,
    Blob: Pt
  },
  isStandardBrowserEnv: _t,
  isStandardBrowserWebWorkerEnv: vt,
  protocols: ["http", "https", "file", "blob", "url", "data"]
};
function At(t, e) {
  return V(t, new T.classes.URLSearchParams(), Object.assign({
    visitor: function(n, i, r, s) {
      return T.isNode && a.isBuffer(n) ? (this.append(i, n.toString("base64")), !1) : s.defaultVisitor.apply(this, arguments);
    }
  }, e));
}
function Ot(t) {
  return a.matchAll(/\w+|\[(\w*)]/g, t).map((e) => e[0] === "[]" ? "" : e[1] || e[0]);
}
function Rt(t) {
  const e = {}, n = Object.keys(t);
  let i;
  const r = n.length;
  let s;
  for (i = 0; i < r; i++)
    s = n[i], e[s] = t[s];
  return e;
}
function Ce(t) {
  function e(n, i, r, s) {
    let o = n[s++];
    const c = Number.isFinite(+o), f = s >= n.length;
    return o = !o && a.isArray(r) ? r.length : o, f ? (a.hasOwnProp(r, o) ? r[o] = [r[o], i] : r[o] = i, !c) : ((!r[o] || !a.isObject(r[o])) && (r[o] = []), e(n, i, r[o], s) && a.isArray(r[o]) && (r[o] = Rt(r[o])), !c);
  }
  if (a.isFormData(t) && a.isFunction(t.entries)) {
    const n = {};
    return a.forEachEntry(t, (i, r) => {
      e(Ot(i), r, n, 0);
    }), n;
  }
  return null;
}
function Ct(t, e, n) {
  if (a.isString(t))
    try {
      return (e || JSON.parse)(t), a.trim(t);
    } catch (i) {
      if (i.name !== "SyntaxError")
        throw i;
    }
  return (n || JSON.stringify)(t);
}
const ee = {
  transitional: Re,
  adapter: T.isNode ? "http" : "xhr",
  transformRequest: [function(e, n) {
    const i = n.getContentType() || "", r = i.indexOf("application/json") > -1, s = a.isObject(e);
    if (s && a.isHTMLForm(e) && (e = new FormData(e)), a.isFormData(e))
      return r && r ? JSON.stringify(Ce(e)) : e;
    if (a.isArrayBuffer(e) || a.isBuffer(e) || a.isStream(e) || a.isFile(e) || a.isBlob(e))
      return e;
    if (a.isArrayBufferView(e))
      return e.buffer;
    if (a.isURLSearchParams(e))
      return n.setContentType("application/x-www-form-urlencoded;charset=utf-8", !1), e.toString();
    let c;
    if (s) {
      if (i.indexOf("application/x-www-form-urlencoded") > -1)
        return At(e, this.formSerializer).toString();
      if ((c = a.isFileList(e)) || i.indexOf("multipart/form-data") > -1) {
        const f = this.env && this.env.FormData;
        return V(
            c ? { "files[]": e } : e,
            f && new f(),
            this.formSerializer
        );
      }
    }
    return s || r ? (n.setContentType("application/json", !1), Ct(e)) : e;
  }],
  transformResponse: [function(e) {
    const n = this.transitional || ee.transitional, i = n && n.forcedJSONParsing, r = this.responseType === "json";
    if (e && a.isString(e) && (i && !this.responseType || r)) {
      const o = !(n && n.silentJSONParsing) && r;
      try {
        return JSON.parse(e);
      } catch (c) {
        if (o)
          throw c.name === "SyntaxError" ? m.from(c, m.ERR_BAD_RESPONSE, this, null, this.response) : c;
      }
    }
    return e;
  }],
  /**
   * A timeout in milliseconds to abort a request. If set to 0 (default) a
   * timeout is not created.
   */
  timeout: 0,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
  maxContentLength: -1,
  maxBodyLength: -1,
  env: {
    FormData: T.classes.FormData,
    Blob: T.classes.Blob
  },
  validateStatus: function(e) {
    return e >= 200 && e < 300;
  },
  headers: {
    common: {
      Accept: "application/json, text/plain, */*",
      "Content-Type": void 0
    }
  }
};
a.forEach(["delete", "get", "head", "post", "put", "patch"], (t) => {
  ee.headers[t] = {};
});
const te = ee, xt = a.toObjectSet([
  "age",
  "authorization",
  "content-length",
  "content-type",
  "etag",
  "expires",
  "from",
  "host",
  "if-modified-since",
  "if-unmodified-since",
  "last-modified",
  "location",
  "max-forwards",
  "proxy-authorization",
  "referer",
  "retry-after",
  "user-agent"
]), Lt = (t) => {
  const e = {};
  let n, i, r;
  return t && t.split(`
`).forEach(function(o) {
    r = o.indexOf(":"), n = o.substring(0, r).trim().toLowerCase(), i = o.substring(r + 1).trim(), !(!n || e[n] && xt[n]) && (n === "set-cookie" ? e[n] ? e[n].push(i) : e[n] = [i] : e[n] = e[n] ? e[n] + ", " + i : i);
  }), e;
}, le = Symbol("internals");
function C(t) {
  return t && String(t).trim().toLowerCase();
}
function D(t) {
  return t === !1 || t == null ? t : a.isArray(t) ? t.map(D) : String(t);
}
function Nt(t) {
  const e = /* @__PURE__ */ Object.create(null), n = /([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g;
  let i;
  for (; i = n.exec(t); )
    e[i[1]] = i[2];
  return e;
}
const It = (t) => /^[-_a-zA-Z0-9^`|~,!#$%&'*+.]+$/.test(t.trim());
function G(t, e, n, i, r) {
  if (a.isFunction(i))
    return i.call(this, e, n);
  if (r && (e = n), !!a.isString(e)) {
    if (a.isString(i))
      return e.indexOf(i) !== -1;
    if (a.isRegExp(i))
      return i.test(e);
  }
}
function Bt(t) {
  return t.trim().toLowerCase().replace(/([a-z\d])(\w*)/g, (e, n, i) => n.toUpperCase() + i);
}
function Dt(t, e) {
  const n = a.toCamelCase(" " + e);
  ["get", "set", "has"].forEach((i) => {
    Object.defineProperty(t, i + n, {
      value: function(r, s, o) {
        return this[i].call(this, e, r, s, o);
      },
      configurable: !0
    });
  });
}
class q {
  constructor(e) {
    e && this.set(e);
  }
  set(e, n, i) {
    const r = this;
    function s(c, f, l) {
      const u = C(f);
      if (!u)
        throw new Error("header name must be a non-empty string");
      const d = a.findKey(r, u);
      (!d || r[d] === void 0 || l === !0 || l === void 0 && r[d] !== !1) && (r[d || f] = D(c));
    }
    const o = (c, f) => a.forEach(c, (l, u) => s(l, u, f));
    return a.isPlainObject(e) || e instanceof this.constructor ? o(e, n) : a.isString(e) && (e = e.trim()) && !It(e) ? o(Lt(e), n) : e != null && s(n, e, i), this;
  }
  get(e, n) {
    if (e = C(e), e) {
      const i = a.findKey(this, e);
      if (i) {
        const r = this[i];
        if (!n)
          return r;
        if (n === !0)
          return Nt(r);
        if (a.isFunction(n))
          return n.call(this, r, i);
        if (a.isRegExp(n))
          return n.exec(r);
        throw new TypeError("parser must be boolean|regexp|function");
      }
    }
  }
  has(e, n) {
    if (e = C(e), e) {
      const i = a.findKey(this, e);
      return !!(i && this[i] !== void 0 && (!n || G(this, this[i], i, n)));
    }
    return !1;
  }
  delete(e, n) {
    const i = this;
    let r = !1;
    function s(o) {
      if (o = C(o), o) {
        const c = a.findKey(i, o);
        c && (!n || G(i, i[c], c, n)) && (delete i[c], r = !0);
      }
    }
    return a.isArray(e) ? e.forEach(s) : s(e), r;
  }
  clear(e) {
    const n = Object.keys(this);
    let i = n.length, r = !1;
    for (; i--; ) {
      const s = n[i];
      (!e || G(this, this[s], s, e, !0)) && (delete this[s], r = !0);
    }
    return r;
  }
  normalize(e) {
    const n = this, i = {};
    return a.forEach(this, (r, s) => {
      const o = a.findKey(i, s);
      if (o) {
        n[o] = D(r), delete n[s];
        return;
      }
      const c = e ? Bt(s) : String(s).trim();
      c !== s && delete n[s], n[c] = D(r), i[c] = !0;
    }), this;
  }
  concat(...e) {
    return this.constructor.concat(this, ...e);
  }
  toJSON(e) {
    const n = /* @__PURE__ */ Object.create(null);
    return a.forEach(this, (i, r) => {
      i != null && i !== !1 && (n[r] = e && a.isArray(i) ? i.join(", ") : i);
    }), n;
  }
  [Symbol.iterator]() {
    return Object.entries(this.toJSON())[Symbol.iterator]();
  }
  toString() {
    return Object.entries(this.toJSON()).map(([e, n]) => e + ": " + n).join(`
`);
  }
  get [Symbol.toStringTag]() {
    return "AxiosHeaders";
  }
  static from(e) {
    return e instanceof this ? e : new this(e);
  }
  static concat(e, ...n) {
    const i = new this(e);
    return n.forEach((r) => i.set(r)), i;
  }
  static accessor(e) {
    const i = (this[le] = this[le] = {
      accessors: {}
    }).accessors, r = this.prototype;
    function s(o) {
      const c = C(o);
      i[c] || (Dt(r, o), i[c] = !0);
    }
    return a.isArray(e) ? e.forEach(s) : s(e), this;
  }
}
q.accessor(["Content-Type", "Content-Length", "Accept", "Accept-Encoding", "User-Agent", "Authorization"]);
a.reduceDescriptors(q.prototype, ({ value: t }, e) => {
  let n = e[0].toUpperCase() + e.slice(1);
  return {
    get: () => t,
    set(i) {
      this[n] = i;
    }
  };
});
a.freezeMethods(q);
const _ = q;
function z(t, e) {
  const n = this || te, i = e || n, r = _.from(i.headers);
  let s = i.data;
  return a.forEach(t, function(c) {
    s = c.call(n, s, r.normalize(), e ? e.status : void 0);
  }), r.normalize(), s;
}
function xe(t) {
  return !!(t && t.__CANCEL__);
}
function N(t, e, n) {
  m.call(this, t ?? "canceled", m.ERR_CANCELED, e, n), this.name = "CanceledError";
}
a.inherits(N, m, {
  __CANCEL__: !0
});
function kt(t, e, n) {
  const i = n.config.validateStatus;
  !n.status || !i || i(n.status) ? t(n) : e(new m(
      "Request failed with status code " + n.status,
      [m.ERR_BAD_REQUEST, m.ERR_BAD_RESPONSE][Math.floor(n.status / 100) - 4],
      n.config,
      n.request,
      n
  ));
}
const Ut = T.isStandardBrowserEnv ? (
    // Standard browser envs support document.cookie
    function() {
      return {
        write: function(n, i, r, s, o, c) {
          const f = [];
          f.push(n + "=" + encodeURIComponent(i)), a.isNumber(r) && f.push("expires=" + new Date(r).toGMTString()), a.isString(s) && f.push("path=" + s), a.isString(o) && f.push("domain=" + o), c === !0 && f.push("secure"), document.cookie = f.join("; ");
        },
        read: function(n) {
          const i = document.cookie.match(new RegExp("(^|;\\s*)(" + n + ")=([^;]*)"));
          return i ? decodeURIComponent(i[3]) : null;
        },
        remove: function(n) {
          this.write(n, "", Date.now() - 864e5);
        }
      };
    }()
) : (
    // Non standard browser env (web workers, react-native) lack needed support.
    function() {
      return {
        write: function() {
        },
        read: function() {
          return null;
        },
        remove: function() {
        }
      };
    }()
);
function Ft(t) {
  return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(t);
}
function Mt(t, e) {
  return e ? t.replace(/\/+$/, "") + "/" + e.replace(/^\/+/, "") : t;
}
function Le(t, e) {
  return t && !Ft(e) ? Mt(t, e) : e;
}
const Ht = T.isStandardBrowserEnv ? (
    // Standard browser envs have full support of the APIs needed to test
    // whether the request URL is of the same origin as current location.
    function() {
      const e = /(msie|trident)/i.test(navigator.userAgent), n = document.createElement("a");
      let i;
      function r(s) {
        let o = s;
        return e && (n.setAttribute("href", o), o = n.href), n.setAttribute("href", o), {
          href: n.href,
          protocol: n.protocol ? n.protocol.replace(/:$/, "") : "",
          host: n.host,
          search: n.search ? n.search.replace(/^\?/, "") : "",
          hash: n.hash ? n.hash.replace(/^#/, "") : "",
          hostname: n.hostname,
          port: n.port,
          pathname: n.pathname.charAt(0) === "/" ? n.pathname : "/" + n.pathname
        };
      }
      return i = r(window.location.href), function(o) {
        const c = a.isString(o) ? r(o) : o;
        return c.protocol === i.protocol && c.host === i.host;
      };
    }()
) : (
    // Non standard browser envs (web workers, react-native) lack needed support.
    function() {
      return function() {
        return !0;
      };
    }()
);
function jt(t) {
  const e = /^([-+\w]{1,25})(:?\/\/|:)/.exec(t);
  return e && e[1] || "";
}
function Vt(t, e) {
  t = t || 10;
  const n = new Array(t), i = new Array(t);
  let r = 0, s = 0, o;
  return e = e !== void 0 ? e : 1e3, function(f) {
    const l = Date.now(), u = i[s];
    o || (o = l), n[r] = f, i[r] = l;
    let d = s, g = 0;
    for (; d !== r; )
      g += n[d++], d = d % t;
    if (r = (r + 1) % t, r === s && (s = (s + 1) % t), l - o < e)
      return;
    const y = u && l - u;
    return y ? Math.round(g * 1e3 / y) : void 0;
  };
}
function ue(t, e) {
  let n = 0;
  const i = Vt(50, 250);
  return (r) => {
    const s = r.loaded, o = r.lengthComputable ? r.total : void 0, c = s - n, f = i(c), l = s <= o;
    n = s;
    const u = {
      loaded: s,
      total: o,
      progress: o ? s / o : void 0,
      bytes: c,
      rate: f || void 0,
      estimated: f && o && l ? (o - s) / f : void 0,
      event: r
    };
    u[e ? "download" : "upload"] = !0, t(u);
  };
}
const qt = typeof XMLHttpRequest < "u", Xt = qt && function(t) {
  return new Promise(function(n, i) {
    let r = t.data;
    const s = _.from(t.headers).normalize(), o = t.responseType;
    let c;
    function f() {
      t.cancelToken && t.cancelToken.unsubscribe(c), t.signal && t.signal.removeEventListener("abort", c);
    }
    a.isFormData(r) && (T.isStandardBrowserEnv || T.isStandardBrowserWebWorkerEnv ? s.setContentType(!1) : s.setContentType("multipart/form-data;", !1));
    let l = new XMLHttpRequest();
    if (t.auth) {
      const y = t.auth.username || "", h = t.auth.password ? unescape(encodeURIComponent(t.auth.password)) : "";
      s.set("Authorization", "Basic " + btoa(y + ":" + h));
    }
    const u = Le(t.baseURL, t.url);
    l.open(t.method.toUpperCase(), Oe(u, t.params, t.paramsSerializer), !0), l.timeout = t.timeout;
    function d() {
      if (!l)
        return;
      const y = _.from(
          "getAllResponseHeaders" in l && l.getAllResponseHeaders()
      ), p = {
        data: !o || o === "text" || o === "json" ? l.responseText : l.response,
        status: l.status,
        statusText: l.statusText,
        headers: y,
        config: t,
        request: l
      };
      kt(function(b) {
        n(b), f();
      }, function(b) {
        i(b), f();
      }, p), l = null;
    }
    if ("onloadend" in l ? l.onloadend = d : l.onreadystatechange = function() {
      !l || l.readyState !== 4 || l.status === 0 && !(l.responseURL && l.responseURL.indexOf("file:") === 0) || setTimeout(d);
    }, l.onabort = function() {
      l && (i(new m("Request aborted", m.ECONNABORTED, t, l)), l = null);
    }, l.onerror = function() {
      i(new m("Network Error", m.ERR_NETWORK, t, l)), l = null;
    }, l.ontimeout = function() {
      let h = t.timeout ? "timeout of " + t.timeout + "ms exceeded" : "timeout exceeded";
      const p = t.transitional || Re;
      t.timeoutErrorMessage && (h = t.timeoutErrorMessage), i(new m(
          h,
          p.clarifyTimeoutError ? m.ETIMEDOUT : m.ECONNABORTED,
          t,
          l
      )), l = null;
    }, T.isStandardBrowserEnv) {
      const y = (t.withCredentials || Ht(u)) && t.xsrfCookieName && Ut.read(t.xsrfCookieName);
      y && s.set(t.xsrfHeaderName, y);
    }
    r === void 0 && s.setContentType(null), "setRequestHeader" in l && a.forEach(s.toJSON(), function(h, p) {
      l.setRequestHeader(p, h);
    }), a.isUndefined(t.withCredentials) || (l.withCredentials = !!t.withCredentials), o && o !== "json" && (l.responseType = t.responseType), typeof t.onDownloadProgress == "function" && l.addEventListener("progress", ue(t.onDownloadProgress, !0)), typeof t.onUploadProgress == "function" && l.upload && l.upload.addEventListener("progress", ue(t.onUploadProgress)), (t.cancelToken || t.signal) && (c = (y) => {
      l && (i(!y || y.type ? new N(null, t, l) : y), l.abort(), l = null);
    }, t.cancelToken && t.cancelToken.subscribe(c), t.signal && (t.signal.aborted ? c() : t.signal.addEventListener("abort", c)));
    const g = jt(u);
    if (g && T.protocols.indexOf(g) === -1) {
      i(new m("Unsupported protocol " + g + ":", m.ERR_BAD_REQUEST, t));
      return;
    }
    l.send(r || null);
  });
}, k = {
  http: gt,
  xhr: Xt
};
a.forEach(k, (t, e) => {
  if (t) {
    try {
      Object.defineProperty(t, "name", { value: e });
    } catch {
    }
    Object.defineProperty(t, "adapterName", { value: e });
  }
});
const Ne = {
  getAdapter: (t) => {
    t = a.isArray(t) ? t : [t];
    const { length: e } = t;
    let n, i;
    for (let r = 0; r < e && (n = t[r], !(i = a.isString(n) ? k[n.toLowerCase()] : n)); r++)
      ;
    if (!i)
      throw i === !1 ? new m(
          `Adapter ${n} is not supported by the environment`,
          "ERR_NOT_SUPPORT"
      ) : new Error(
          a.hasOwnProp(k, n) ? `Adapter '${n}' is not available in the build` : `Unknown adapter '${n}'`
      );
    if (!a.isFunction(i))
      throw new TypeError("adapter is not a function");
    return i;
  },
  adapters: k
};
function W(t) {
  if (t.cancelToken && t.cancelToken.throwIfRequested(), t.signal && t.signal.aborted)
    throw new N(null, t);
}
function de(t) {
  return W(t), t.headers = _.from(t.headers), t.data = z.call(
      t,
      t.transformRequest
  ), ["post", "put", "patch"].indexOf(t.method) !== -1 && t.headers.setContentType("application/x-www-form-urlencoded", !1), Ne.getAdapter(t.adapter || te.adapter)(t).then(function(i) {
    return W(t), i.data = z.call(
        t,
        t.transformResponse,
        i
    ), i.headers = _.from(i.headers), i;
  }, function(i) {
    return xe(i) || (W(t), i && i.response && (i.response.data = z.call(
        t,
        t.transformResponse,
        i.response
    ), i.response.headers = _.from(i.response.headers))), Promise.reject(i);
  });
}
const fe = (t) => t instanceof _ ? t.toJSON() : t;
function O(t, e) {
  e = e || {};
  const n = {};
  function i(l, u, d) {
    return a.isPlainObject(l) && a.isPlainObject(u) ? a.merge.call({ caseless: d }, l, u) : a.isPlainObject(u) ? a.merge({}, u) : a.isArray(u) ? u.slice() : u;
  }
  function r(l, u, d) {
    if (a.isUndefined(u)) {
      if (!a.isUndefined(l))
        return i(void 0, l, d);
    } else
      return i(l, u, d);
  }
  function s(l, u) {
    if (!a.isUndefined(u))
      return i(void 0, u);
  }
  function o(l, u) {
    if (a.isUndefined(u)) {
      if (!a.isUndefined(l))
        return i(void 0, l);
    } else
      return i(void 0, u);
  }
  function c(l, u, d) {
    if (d in e)
      return i(l, u);
    if (d in t)
      return i(void 0, l);
  }
  const f = {
    url: s,
    method: s,
    data: s,
    baseURL: o,
    transformRequest: o,
    transformResponse: o,
    paramsSerializer: o,
    timeout: o,
    timeoutMessage: o,
    withCredentials: o,
    adapter: o,
    responseType: o,
    xsrfCookieName: o,
    xsrfHeaderName: o,
    onUploadProgress: o,
    onDownloadProgress: o,
    decompress: o,
    maxContentLength: o,
    maxBodyLength: o,
    beforeRedirect: o,
    transport: o,
    httpAgent: o,
    httpsAgent: o,
    cancelToken: o,
    socketPath: o,
    responseEncoding: o,
    validateStatus: c,
    headers: (l, u) => r(fe(l), fe(u), !0)
  };
  return a.forEach(Object.keys(Object.assign({}, t, e)), function(u) {
    const d = f[u] || r, g = d(t[u], e[u], u);
    a.isUndefined(g) && d !== c || (n[u] = g);
  }), n;
}
const Ie = "1.5.0", ne = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach((t, e) => {
  ne[t] = function(i) {
    return typeof i === t || "a" + (e < 1 ? "n " : " ") + t;
  };
});
const he = {};
ne.transitional = function(e, n, i) {
  function r(s, o) {
    return "[Axios v" + Ie + "] Transitional option '" + s + "'" + o + (i ? ". " + i : "");
  }
  return (s, o, c) => {
    if (e === !1)
      throw new m(
          r(o, " has been removed" + (n ? " in " + n : "")),
          m.ERR_DEPRECATED
      );
    return n && !he[o] && (he[o] = !0, console.warn(
        r(
            o,
            " has been deprecated since v" + n + " and will be removed in the near future"
        )
    )), e ? e(s, o, c) : !0;
  };
};
function Gt(t, e, n) {
  if (typeof t != "object")
    throw new m("options must be an object", m.ERR_BAD_OPTION_VALUE);
  const i = Object.keys(t);
  let r = i.length;
  for (; r-- > 0; ) {
    const s = i[r], o = e[s];
    if (o) {
      const c = t[s], f = c === void 0 || o(c, s, t);
      if (f !== !0)
        throw new m("option " + s + " must be " + f, m.ERR_BAD_OPTION_VALUE);
      continue;
    }
    if (n !== !0)
      throw new m("Unknown option " + s, m.ERR_BAD_OPTION);
  }
}
const Y = {
  assertOptions: Gt,
  validators: ne
}, v = Y.validators;
class F {
  constructor(e) {
    this.defaults = e, this.interceptors = {
      request: new ce(),
      response: new ce()
    };
  }
  /**
   * Dispatch a request
   *
   * @param {String|Object} configOrUrl The config specific for this request (merged with this.defaults)
   * @param {?Object} config
   *
   * @returns {Promise} The Promise to be fulfilled
   */
  request(e, n) {
    typeof e == "string" ? (n = n || {}, n.url = e) : n = e || {}, n = O(this.defaults, n);
    const { transitional: i, paramsSerializer: r, headers: s } = n;
    i !== void 0 && Y.assertOptions(i, {
      silentJSONParsing: v.transitional(v.boolean),
      forcedJSONParsing: v.transitional(v.boolean),
      clarifyTimeoutError: v.transitional(v.boolean)
    }, !1), r != null && (a.isFunction(r) ? n.paramsSerializer = {
      serialize: r
    } : Y.assertOptions(r, {
      encode: v.function,
      serialize: v.function
    }, !0)), n.method = (n.method || this.defaults.method || "get").toLowerCase();
    let o = s && a.merge(
        s.common,
        s[n.method]
    );
    s && a.forEach(
        ["delete", "get", "head", "post", "put", "patch", "common"],
        (h) => {
          delete s[h];
        }
    ), n.headers = _.concat(o, s);
    const c = [];
    let f = !0;
    this.interceptors.request.forEach(function(p) {
      typeof p.runWhen == "function" && p.runWhen(n) === !1 || (f = f && p.synchronous, c.unshift(p.fulfilled, p.rejected));
    });
    const l = [];
    this.interceptors.response.forEach(function(p) {
      l.push(p.fulfilled, p.rejected);
    });
    let u, d = 0, g;
    if (!f) {
      const h = [de.bind(this), void 0];
      for (h.unshift.apply(h, c), h.push.apply(h, l), g = h.length, u = Promise.resolve(n); d < g; )
        u = u.then(h[d++], h[d++]);
      return u;
    }
    g = c.length;
    let y = n;
    for (d = 0; d < g; ) {
      const h = c[d++], p = c[d++];
      try {
        y = h(y);
      } catch (P) {
        p.call(this, P);
        break;
      }
    }
    try {
      u = de.call(this, y);
    } catch (h) {
      return Promise.reject(h);
    }
    for (d = 0, g = l.length; d < g; )
      u = u.then(l[d++], l[d++]);
    return u;
  }
  getUri(e) {
    e = O(this.defaults, e);
    const n = Le(e.baseURL, e.url);
    return Oe(n, e.params, e.paramsSerializer);
  }
}
a.forEach(["delete", "get", "head", "options"], function(e) {
  F.prototype[e] = function(n, i) {
    return this.request(O(i || {}, {
      method: e,
      url: n,
      data: (i || {}).data
    }));
  };
});
a.forEach(["post", "put", "patch"], function(e) {
  function n(i) {
    return function(s, o, c) {
      return this.request(O(c || {}, {
        method: e,
        headers: i ? {
          "Content-Type": "multipart/form-data"
        } : {},
        url: s,
        data: o
      }));
    };
  }
  F.prototype[e] = n(), F.prototype[e + "Form"] = n(!0);
});
const U = F;
class ie {
  constructor(e) {
    if (typeof e != "function")
      throw new TypeError("executor must be a function.");
    let n;
    this.promise = new Promise(function(s) {
      n = s;
    });
    const i = this;
    this.promise.then((r) => {
      if (!i._listeners)
        return;
      let s = i._listeners.length;
      for (; s-- > 0; )
        i._listeners[s](r);
      i._listeners = null;
    }), this.promise.then = (r) => {
      let s;
      const o = new Promise((c) => {
        i.subscribe(c), s = c;
      }).then(r);
      return o.cancel = function() {
        i.unsubscribe(s);
      }, o;
    }, e(function(s, o, c) {
      i.reason || (i.reason = new N(s, o, c), n(i.reason));
    });
  }
  /**
   * Throws a `CanceledError` if cancellation has been requested.
   */
  throwIfRequested() {
    if (this.reason)
      throw this.reason;
  }
  /**
   * Subscribe to the cancel signal
   */
  subscribe(e) {
    if (this.reason) {
      e(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(e) : this._listeners = [e];
  }
  /**
   * Unsubscribe from the cancel signal
   */
  unsubscribe(e) {
    if (!this._listeners)
      return;
    const n = this._listeners.indexOf(e);
    n !== -1 && this._listeners.splice(n, 1);
  }
  /**
   * Returns an object that contains a new `CancelToken` and a function that, when called,
   * cancels the `CancelToken`.
   */
  static source() {
    let e;
    return {
      token: new ie(function(r) {
        e = r;
      }),
      cancel: e
    };
  }
}
const zt = ie;
function Wt(t) {
  return function(n) {
    return t.apply(null, n);
  };
}
function $t(t) {
  return a.isObject(t) && t.isAxiosError === !0;
}
const K = {
  Continue: 100,
  SwitchingProtocols: 101,
  Processing: 102,
  EarlyHints: 103,
  Ok: 200,
  Created: 201,
  Accepted: 202,
  NonAuthoritativeInformation: 203,
  NoContent: 204,
  ResetContent: 205,
  PartialContent: 206,
  MultiStatus: 207,
  AlreadyReported: 208,
  ImUsed: 226,
  MultipleChoices: 300,
  MovedPermanently: 301,
  Found: 302,
  SeeOther: 303,
  NotModified: 304,
  UseProxy: 305,
  Unused: 306,
  TemporaryRedirect: 307,
  PermanentRedirect: 308,
  BadRequest: 400,
  Unauthorized: 401,
  PaymentRequired: 402,
  Forbidden: 403,
  NotFound: 404,
  MethodNotAllowed: 405,
  NotAcceptable: 406,
  ProxyAuthenticationRequired: 407,
  RequestTimeout: 408,
  Conflict: 409,
  Gone: 410,
  LengthRequired: 411,
  PreconditionFailed: 412,
  PayloadTooLarge: 413,
  UriTooLong: 414,
  UnsupportedMediaType: 415,
  RangeNotSatisfiable: 416,
  ExpectationFailed: 417,
  ImATeapot: 418,
  MisdirectedRequest: 421,
  UnprocessableEntity: 422,
  Locked: 423,
  FailedDependency: 424,
  TooEarly: 425,
  UpgradeRequired: 426,
  PreconditionRequired: 428,
  TooManyRequests: 429,
  RequestHeaderFieldsTooLarge: 431,
  UnavailableForLegalReasons: 451,
  InternalServerError: 500,
  NotImplemented: 501,
  BadGateway: 502,
  ServiceUnavailable: 503,
  GatewayTimeout: 504,
  HttpVersionNotSupported: 505,
  VariantAlsoNegotiates: 506,
  InsufficientStorage: 507,
  LoopDetected: 508,
  NotExtended: 510,
  NetworkAuthenticationRequired: 511
};
Object.entries(K).forEach(([t, e]) => {
  K[e] = t;
});
const Jt = K;
function Be(t) {
  const e = new U(t), n = me(U.prototype.request, e);
  return a.extend(n, U.prototype, e, { allOwnKeys: !0 }), a.extend(n, e, null, { allOwnKeys: !0 }), n.create = function(r) {
    return Be(O(t, r));
  }, n;
}
const E = Be(te);
E.Axios = U;
E.CanceledError = N;
E.CancelToken = zt;
E.isCancel = xe;
E.VERSION = Ie;
E.toFormData = V;
E.AxiosError = m;
E.Cancel = E.CanceledError;
E.all = function(e) {
  return Promise.all(e);
};
E.spread = Wt;
E.isAxiosError = $t;
E.mergeConfig = O;
E.AxiosHeaders = _;
E.formToJSON = (t) => Ce(a.isHTMLForm(t) ? new FormData(t) : t);
E.getAdapter = Ne.getAdapter;
E.HttpStatusCode = Jt;
E.default = E;
const pe = E;
class Yt {
  constructor(e) {
    this.config = e, this.config.postConfig = this.config.postConfig || {
      captchaParamName: "_tianaiCaptcha",
      tokenParamName: "_tianaiCaptchaToken"
    }, this.config.slider = this.config.slider || this.sliderConfig(), this.config.rotate = this.config.rotate || this.rotateConfig(), this.config.concat = this.config.concat || this.concatConfig(), this.config.dateFormat = this.config.dateFormat || "yyyy-MM-dd HH:mm:ss'", this.config.loadingText = this.config.loadingText || "", this.config.title = this.config.title || "安全验证", this.config.validText = this.config.validText || "", this.config.width = this.config.width || 400, this.config.showMerchantName = this.config.showMerchantName === void 0 ? !0 : this.config.showMerchantName, this.config.success = this.config.success || console.info, this.config.fail = this.config.fail || console.error, this.config.failRefreshCaptcha = this.config.failRefreshCaptcha === void 0 ? !0 : this.config.failRefreshCaptcha, this.config.baseUrl || (this.config.baseUrl = "$baseUrlToken$"), this.http = pe.create({ baseURL: this.config.baseUrl }), this._onGlobalPointerMove = this.move.bind(this), this._onGlobalPointerUp = this.up.bind(this), this._onSliderDown = this.sliderDown.bind(this), this._globalPointerCapture = !0, this._pointerDragEl = null, this._pointerCaptureId = null, this.containerTemplate = '<div class="__tianai-container" id="tianai-container"></div>', this.contentTemplate = `
        <div class="__tianai-content" style="width:${this.config.width}px" id="tianai-content">
          <div class="__tianai-content-title" id="tianai-content-title">
            <div class="__tianai-content-title-text">${this.config.title}</div>
            <div class="__tianai-content-title-close" id="tianai-content-close-btn"></div>
          </div>
        </div>
    `, this.imageContentTemplate = `
        <div class="__tianai-content-image-bg" id="tianai-content-image-bg">
        </div>
        <div class="__tianai-content-target" id="tianai-image-content-target">
        </div>
    `, this.contentWrapper = '<div class="__tianai-content-image-wrapper" id="tianai-content-image-wrapper"></div>', this.contentOperate = `
        <div class="__tianai-slider-move">
          <div class="__tianai-slider-move-track" id="tianai-slider-move-track">
            
          </div>
          <div class="__tianai-btn __tianai-slider-move-btn" id="tianai-slider-move-btn">
          </div>
        </div>
        <div class="__tianai-operating">
        
          <div class="__tianai-operating-merchant" id="tianai-operating-merchant">
            
          </div>
          
          <div class="__tianai-operating-btn">
            <div class="__tianai-btn __tianai-operating-refresh-btn" id="tianai-operating-refresh-btn">
            </div>
            <div class="__tianai-btn __tianai-operating-close-btn" id="tianai-operating-close-btn">
            </div>
          </div>
        </div>
    `;
  }
  sliderConfig() {
    return {
      prompt: "滑动拼图块完成验证",
      onMove: this.sliderMove
    };
  }
  rotateConfig() {
    return {
      prompt: "滑动旋转角度完成验证",
      onMove: this.rotateMove
    };
  }
  concatConfig() {
    return {
      prompt: "滑动拼接图片完成验证",
      onMove: this.concatMove
    };
  }
  show() {
    if ({}.VITE_NODE_ENV !== "development")
      if (document.querySelector("link[id='tianai-captcha']"))
        this.doShow();
      else {
        let n = document.createElement("link");
        n.id = "tianai-captcha", n.type = "text/css", n.href = this.config.baseUrl + "/tianai-captcha.css", n.rel = "stylesheet", n.onerror = this.config.fail, n.onload = () => this.doShow(), document.body.appendChild(n);
      }
    else
      this.doShow();
  }
  doShow() {
    if (!document.getElementById("tianai-content")) {
      let n = this.config.container;
      n ? n = document.getElementById(this.config.container) : n = document.body, n.insertAdjacentHTML("beforeend", this.containerTemplate), n.lastElementChild.insertAdjacentHTML("beforeend", this.contentTemplate), this.fadeIn(n.lastElementChild), document.getElementById("tianai-content-close-btn").addEventListener("click", this.hide.bind(this));
    }
    this.generateCaptcha();
  }
  generateCaptcha(e = !0) {
    e && this.loading(this.config.loadingText);
    let n = this.config.postConfig.tokenParamName + "=" + this.config.token + "&captchaType=tianai&generateImageType=" + (this.config.generateType || "random");
    return this.http.get("/captcha/generateCaptcha?" + n).then((i) => this.doGenerateHtml(i.data.data)).catch((i) => {
      if (i.response.data) {
        const r = i.response.data;
        r.executeCode && r.executeCode === "10404" ? pe.get(this.config.baseUrl + "/captcha/generateToken?type=tianai").then((s) => {
          this.config.token = s.data.data.token.name, this.generateCaptcha(e);
        }) : this.config.fail(i);
      } else
        this.config.fail(i);
    });
  }
  doGenerateHtml(e) {
    var f;
    this.generateData = e, this.removeLoading(), this.detachGlobalPointerListeners();
    const n = document.getElementById("tianai-content-title");
    let i = document.getElementById("tianai-slider-move-btn");
    if (!i && (n.insertAdjacentHTML("afterend", this.contentOperate), i = document.getElementById("tianai-slider-move-btn"), typeof window < "u" && window.PointerEvent ? i.addEventListener("pointerdown", this._onSliderDown, { passive: !1 }) : (i.addEventListener("mousedown", this._onSliderDown), i.addEventListener("touchstart", this._onSliderDown, { passive: !1 })), document.getElementById("tianai-operating-refresh-btn").addEventListener("click", this.generateCaptcha.bind(this)), document.getElementById("tianai-operating-close-btn").addEventListener("click", this.hide.bind(this)), this.config.showMerchantName && e.merchantName)) {
      const d = document.getElementById("tianai-operating-merchant");
      d.innerHTML = e.merchantName || "";
    }
    this.applySliderTouchStyle(i);
    let r = document.getElementById("tianai-content-image-wrapper");
    if (!r)
      n.insertAdjacentHTML("afterend", this.contentWrapper), r = document.getElementById("tianai-content-image-wrapper"), r.insertAdjacentHTML("beforeend", this.imageContentTemplate);
    else {
      const l = document.getElementById("tianai-content-image-bg");
      this.removeElement(l);
      const u = document.getElementById("tianai-image-content-target");
      this.removeElement(u), r.insertAdjacentHTML("beforeend", this.imageContentTemplate);
    }
    const s = document.getElementById("tianai-content-image-bg");
    let o = s.lastElementChild;
    o ? o.src = e.backgroundImage : s.insertAdjacentHTML("beforeend", `<img src="${e.backgroundImage}">`), this.validValue = {
      startX: 0,
      startY: 0,
      startTime: /* @__PURE__ */ new Date(),
      trackArr: []
    };
    const c = document.getElementById("tianai-image-content-target");
    if (["slider", "rotate"].includes(e.type)) {
      const l = document.getElementById("image-content-target-template");
      l ? l.src = e.templateImage : c.insertAdjacentHTML("beforeend", `<img src="${e.templateImage}" id="image-content-target-template">`);
    } else {
      const l = document.getElementById("image-content-target-template");
      l && this.fadeOut(l), c.style.backgroundImage = "url(" + e.backgroundImage + ")", c.style.backgroundPosition = "0px 0px";
      const u = e.backgroundImageHeight;
      c.style.height = u - e.data.viewData.randomY + "px";
    }
    document.getElementById("tianai-slider-move-track").innerHTML = ((f = this.config[e.type]) == null ? void 0 : f.prompt) || "拖动完成验证", setTimeout(() => {
      this.validValue.bgImageWidth = s.offsetWidth, this.validValue.bgImageHeight = s.offsetHeight, this.validValue.end = this.config.width - i.offsetWidth, e.type === "slider" ? (this.validValue.sliderImageWidth = c.lastElementChild.offsetWidth, this.validValue.sliderImageHeight = c.lastElementChild.offsetHeight) : e.type === "rotate" && (this.validValue.bgImageWidth = e.degree + e.randomX - this.validValue.bgImageHeight / 10);
    }, 500), c.classList.add(e.type.toLowerCase()), this.returnToActualPosition(i);
  }
  removeElement(e) {
    e && e.remove();
  }
  returnToActualPosition(e) {
    e.style.transform !== "" && e.style.transform !== "translate(0px, 0px)" && (e.style.transition = "transform 0.5s ease", e.style.transform = "translate(0px, 0px)", e.addEventListener("transitionend", (n) => e.style.transition = "none"));
  }
  fadeIn(e) {
    e.style.transition = "opacity 0.5s ease", e.style.opacity = "0", setTimeout(() => {
      e.style.opacity = "1", e.addEventListener("transitionend", () => e.style.transition = "none");
    }, 100);
  }
  fadeOut(e) {
    e.style.transition = "opacity 0.5s ease", e.style.opacity = "0", e.addEventListener("transitionend", () => e.remove());
  }
  formUrlEncoded(e, n, i) {
    let r = new URLSearchParams(), s = [];
    typeof n == "string" ? s.push(n) : s = n || [];
    for (let o in e) {
      if (s.includes(o))
        continue;
      let c = e[o];
      c != null && (i && (c = i(o, c)), Array.isArray(c) ? c.forEach((f) => r.append(o, f)) : r.append(o, c));
    }
    return r;
  }
  hide(e) {
    const n = document.getElementById("tianai-container");
    n && (this.detachGlobalPointerListeners(), this.fadeOut(n));
  }
  detachGlobalPointerListeners() {
    if (this._pointerDragEl) {
      if (this._pointerDragEl.removeEventListener("pointermove", this._onGlobalPointerMove), this._pointerDragEl.removeEventListener("pointerup", this._onGlobalPointerUp), this._pointerDragEl.removeEventListener("pointercancel", this._onGlobalPointerUp), this._pointerCaptureId != null)
        try {
          this._pointerDragEl.hasPointerCapture && this._pointerDragEl.hasPointerCapture(this._pointerCaptureId) && this._pointerDragEl.releasePointerCapture(this._pointerCaptureId);
        } catch {
        }
      this._pointerDragEl = null, this._pointerCaptureId = null;
    }
    const e = this._globalPointerCapture, n = { capture: e, passive: !1 };
    document.removeEventListener("mousemove", this._onGlobalPointerMove, e), document.removeEventListener("mouseup", this._onGlobalPointerUp, e), document.removeEventListener("touchmove", this._onGlobalPointerMove, n), document.removeEventListener("touchend", this._onGlobalPointerUp, e), document.removeEventListener("touchcancel", this._onGlobalPointerUp, e);
  }
  applySliderTouchStyle(e) {
    if (!e)
      return;
    e.style.touchAction = "none", e.style.webkitUserSelect = "none", e.style.userSelect = "none";
    const n = e.parentElement;
    n && (n.style.touchAction = "none");
  }
  getPointerPageXY(e) {
    if (!e)
      return { x: 0, y: 0 };
    let n = e.pageX, i = e.pageY;
    if (n == null || i == null) {
      const r = window.scrollX != null ? window.scrollX : window.pageXOffset, s = window.scrollY != null ? window.scrollY : window.pageYOffset;
      n = e.clientX + r, i = e.clientY + s;
    }
    return { x: Math.round(n), y: Math.round(i) };
  }
  sliderDown(e) {
    if (typeof window < "u" && window.PointerEvent && e instanceof PointerEvent) {
      if (!e.isPrimary || e.pointerType === "mouse" && e.button !== 0)
        return;
    } else if (e.type === "mousedown" && e.button !== 0)
      return;
    e.cancelable && (e.type === "touchstart" || typeof window < "u" && window.PointerEvent && e instanceof PointerEvent && e.pointerType === "touch") && e.preventDefault();
    let n, i;
    if (typeof window < "u" && window.PointerEvent && e instanceof PointerEvent) {
      const d = this.getPointerPageXY(e);
      n = d.x, i = d.y;
    } else if (typeof TouchEvent < "u" && e instanceof TouchEvent) {
      const d = e.targetTouches && e.targetTouches[0] || e.touches[0], g = this.getPointerPageXY(d);
      n = g.x, i = g.y;
    } else {
      const d = this.getPointerPageXY(e);
      n = d.x, i = d.y;
    }
    this.validValue.startX = n, this.validValue.startY = i;
    const r = this.validValue.startX, s = this.validValue.startY, o = this.validValue.startTime;
    if (this.validValue.trackArr.push({
      x: r - n,
      y: s - i,
      type: "down",
      t: (/* @__PURE__ */ new Date()).getTime() - o.getTime()
    }), this.detachGlobalPointerListeners(), typeof window < "u" && window.PointerEvent && e instanceof PointerEvent) {
      const d = e.currentTarget;
      try {
        d.setPointerCapture(e.pointerId);
      } catch {
      }
      this._pointerDragEl = d, this._pointerCaptureId = e.pointerId, d.addEventListener("pointermove", this._onGlobalPointerMove), d.addEventListener("pointerup", this._onGlobalPointerUp), d.addEventListener("pointercancel", this._onGlobalPointerUp);
      return;
    }
    const l = this._globalPointerCapture, u = { capture: l, passive: !1 };
    document.addEventListener("mousemove", this._onGlobalPointerMove, l), document.addEventListener("mouseup", this._onGlobalPointerUp, l), document.addEventListener("touchmove", this._onGlobalPointerMove, u), document.addEventListener("touchend", this._onGlobalPointerUp, l), document.addEventListener("touchcancel", this._onGlobalPointerUp, l);
  }
  move(e) {
    let n = e;
    if (typeof TouchEvent < "u" && e instanceof TouchEvent) {
      if (e.cancelable && e.preventDefault(), n = e.touches[0] || e.changedTouches[0], !n)
        return;
    } else
      typeof window < "u" && window.PointerEvent && e instanceof PointerEvent && (e.pointerType === "touch" && e.cancelable && e.preventDefault(), n = e);
    const i = this.getPointerPageXY(n);
    let r = i.x, s = i.y;
    const o = this.validValue.startX, c = this.validValue.startY, f = this.validValue.startTime, l = this.validValue.end, u = this.validValue.bgImageWidth, d = this.validValue.trackArr;
    let g = r - o;
    const y = {
      x: r - o,
      y: s - c,
      type: "move",
      t: (/* @__PURE__ */ new Date()).getTime() - f.getTime()
    };
    d.push(y), g < 0 ? g = 0 : g > l && (g = l), this.validValue.moveX = g, this.validValue.movePercent = g / u, document.getElementById("tianai-slider-move-btn").style.transform = "translate(" + g + "px, 0px)", this.config[this.generateData.type].onMove.bind(this)();
  }
  sliderMove() {
    const e = this.validValue.moveX;
    document.getElementById("tianai-image-content-target").style.transform = "translate(" + e + "px, 0px)";
  }
  rotateMove() {
    const e = this.validValue.moveX;
    document.getElementById("image-content-target-template").style.transform = "rotate(" + e / (this.validValue.end / 360) + "deg)";
  }
  concatMove() {
    const e = this.validValue.moveX;
    document.getElementById("tianai-image-content-target").style.backgroundPositionX = e + "px";
  }
  up(e) {
    this.detachGlobalPointerListeners();
    let n = e;
    e && typeof TouchEvent < "u" && e instanceof TouchEvent ? n = e.changedTouches[0] || e.touches[0] : e && typeof window < "u" && window.PointerEvent && e instanceof PointerEvent && (n = e), this.validValue.stopTime = /* @__PURE__ */ new Date();
    let i, r;
    if (n) {
      const u = this.getPointerPageXY(n);
      i = u.x, r = u.y;
    } else
      i = Math.round(this.validValue.startX + (this.validValue.moveX || 0)), r = Math.round(this.validValue.startY);
    const s = this.validValue.startX, o = this.validValue.startY, c = this.validValue.startTime, f = this.validValue.trackArr, l = {
      x: i - s,
      y: r - o,
      type: "up",
      t: (/* @__PURE__ */ new Date()).getTime() - c.getTime()
    };
    f.push(l), this.valid();
  }
  valid() {
    const e = {
      bgImageWidth: this.validValue.bgImageWidth,
      bgImageHeight: this.validValue.bgImageHeight,
      templateImageWidth: this.generateData.templateImageWidth,
      templateImageHeight: this.generateData.templateImageHeight,
      startTime: this.validValue.startTime.getTime(),
      //this.formatDate(this.validValue.startTime, this.config.dateFormat),
      stopTime: this.validValue.stopTime.getTime(),
      //this.formatDate(this.validValue.stopTime, this.config.dateFormat),
      trackList: this.validValue.trackArr
    };
    let n = document.getElementById("tianai-content-image-wrapper-alert");
    n && this.fadeOut(n), this.alertTimeout && (clearTimeout(this.alertTimeout), delete this.alertTimeout), this.loading(this.config.validText), this.http.post("/captcha/clientVerify?" + this.config.postConfig.tokenParamName + "=" + this.config.token, e).then((i) => this.showResult(i.data)).catch((i) => {
      this.config.failRefreshCaptcha && this.generateCaptcha(), this.config.fail(i);
    });
  }
  formatDate(e, n) {
    const i = e.getFullYear(), r = String(e.getMonth() + 1).padStart(2, "0"), s = String(e.getDate()).padStart(2, "0"), o = String(e.getHours()).padStart(2, "0"), c = String(e.getMinutes()).padStart(2, "0"), f = String(e.getSeconds()).padStart(2, "0");
    return n = n.replace("yyyy", i), n = n.replace("MM", r), n = n.replace("dd", s), n = n.replace("HH", o), n = n.replace("mm", c), n = n.replace("ss", f), n;
  }
  removeLoading() {
    const e = document.getElementById("tianai-loading");
    e && this.fadeOut(e);
  }
  loading(e) {
    if (document.getElementById("tianai-loading"))
      return;
    let i = document.getElementById("tianai-content");
    const r = `
    <div class="__tianai-content-loading" id="tianai-loading">
      <div class="__tianai-content-loading-loader"></div>
      <div class="__tianai-content-loading-text">
        ${e}
      </div>
    </div>
    `;
    this.fadeIn(i.lastElementChild), i.insertAdjacentHTML("beforeend", r);
  }
  showResult(e) {
    const n = document.getElementById("tianai-content-image-wrapper"), i = `
      <div class="__tianai-content-image-wrapper-alert ${e.executeCode === "200" ? "success" : "error"}" id="tianai-content-image-wrapper-alert">
        ${e.message}
      </div>
    `;
    n.insertAdjacentHTML("beforeend", i);
    const r = n.lastElementChild;
    this.fadeIn(r), this.alertTimeout = setTimeout(() => this.fadeOut(r), 3e3), e.executeCode === "200" ? (this.removeLoading(), this.config.success(e)) : (this.config.fail(e), this.config.failRefreshCaptcha && this.generateCaptcha(!1));
  }
}
window.TianaiCaptcha = Yt;
