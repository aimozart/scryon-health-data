// Post an evidence receipt from a Scala 2.13 job (JDK 17 java.net.http, no dependencies). Synthetic example.
// Endpoint (POST /v1/evidence): next node release.
import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

object EmitEvidence {
  private def esc(s: String): String = s.flatMap {
    case '"'  => "\\\""
    case '\\' => "\\\\"
    case '\n' => "\\n"
    case c    => c.toString
  }

  /** The silver-layer invariant: valid + quarantined must equal input. */
  def silverRun(nodeUrl: String, input: Long, valid: Long, quarantined: Long, night: String): String = {
    val ok = valid + quarantined == input
    val body =
      s"""{"kind":"evidence.pipeline_run","label":"silver","decision":"${if (ok) "allow" else "deny"}",""" +
        s""""reason":"${if (ok) "check_passed" else "check_failed"}","control":"hipaa_164_312_c1",""" +
        s""""total":$input,"passed":$valid,"flagged":$quarantined,""" +
        s""""detail":"${esc(s"night=$night layer=silver")}","run_id":"run-$night-s"}"""
    val req = HttpRequest
      .newBuilder(URI.create(nodeUrl + "/v1/evidence"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .build()
    HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString()).body()
  }
}
