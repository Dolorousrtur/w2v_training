import scala.annotation.tailrec

class Rational(x: Int, y: Int){
  def numer = x
  def denom = y

  override def toString: String = s"$numer/$denom"

  def +(that: Rational) =
    new Rational(numer * that.denom + that.numer * denom,
      denom * that.denom)

  def -(that: Rational) =
    new Rational(numer * that.denom - that.numer * denom,
      denom * that.denom)

  def *(that: Rational) =
    new Rational(numer * that.numer,
      denom * that.denom)

  def unary_- = new Rational(-numer, denom)
}

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x - y - z


