import AppKit
import Foundation

let canvasWidth: CGFloat = 1600
let canvasHeight: CGFloat = 980

let titleColor = NSColor(calibratedRed: 23 / 255, green: 50 / 255, blue: 77 / 255, alpha: 1)
let borderColor = NSColor(calibratedRed: 31 / 255, green: 78 / 255, blue: 121 / 255, alpha: 1)
let fillColor = NSColor(calibratedRed: 248 / 255, green: 251 / 255, blue: 1, alpha: 1)
let headerColor = NSColor(calibratedRed: 217 / 255, green: 236 / 255, blue: 1, alpha: 1)
let labelColor = NSColor(calibratedRed: 51 / 255, green: 78 / 255, blue: 104 / 255, alpha: 1)
let noteFillColor = NSColor(calibratedRed: 1, green: 244 / 255, blue: 207 / 255, alpha: 1)
let noteBorderColor = NSColor(calibratedRed: 181 / 255, green: 129 / 255, blue: 5 / 255, alpha: 1)
let noteTextColor = NSColor(calibratedRed: 92 / 255, green: 61 / 255, blue: 0, alpha: 1)

func rect(_ x: CGFloat, _ y: CGFloat, _ width: CGFloat, _ height: CGFloat) -> NSRect {
    NSRect(x: x, y: canvasHeight - y - height, width: width, height: height)
}

func point(_ x: CGFloat, _ y: CGFloat) -> NSPoint {
    NSPoint(x: x, y: canvasHeight - y)
}

func drawText(_ text: String, x: CGFloat, y: CGFloat, width: CGFloat, font: NSFont,
              color: NSColor, alignment: NSTextAlignment = .left) {
    let paragraphStyle = NSMutableParagraphStyle()
    paragraphStyle.alignment = alignment

    let attributes: [NSAttributedString.Key: Any] = [
        .font: font,
        .foregroundColor: color,
        .paragraphStyle: paragraphStyle
    ]

    NSString(string: text).draw(in: rect(x, y, width, font.pointSize + 10), withAttributes: attributes)
}

func drawClassBox(x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat, title: String, lines: [String]) {
    let outer = NSBezierPath(roundedRect: rect(x, y, width, height), xRadius: 10, yRadius: 10)
    fillColor.setFill()
    outer.fill()
    borderColor.setStroke()
    outer.lineWidth = 2
    outer.stroke()

    let header = NSBezierPath(rect: rect(x, y, width, 38))
    headerColor.setFill()
    header.fill()

    let divider = NSBezierPath()
    divider.move(to: point(x, y + 38))
    divider.line(to: point(x + width, y + 38))
    divider.lineWidth = 1.5
    borderColor.setStroke()
    divider.stroke()

    drawText(title, x: x, y: y + 7, width: width, font: NSFont.boldSystemFont(ofSize: 18), color: titleColor, alignment: .center)

    for (index, line) in lines.enumerated() {
        drawText(
                line,
                x: x + 16,
                y: y + 48 + CGFloat(index * 24),
                width: width - 32,
                font: NSFont.systemFont(ofSize: 15),
                color: NSColor(calibratedRed: 31 / 255, green: 41 / 255, blue: 51 / 255, alpha: 1)
        )
    }
}

func drawNote(x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat, lines: [String]) {
    let note = NSBezierPath(roundedRect: rect(x, y, width, height), xRadius: 10, yRadius: 10)
    noteFillColor.setFill()
    note.fill()
    noteBorderColor.setStroke()
    note.lineWidth = 2
    note.stroke()

    for (index, line) in lines.enumerated() {
        drawText(
                line,
                x: x + 20,
                y: y + 14 + CGFloat(index * 24),
                width: width - 40,
                font: index == 0 ? NSFont.boldSystemFont(ofSize: 14) : NSFont.systemFont(ofSize: 14),
                color: noteTextColor
        )
    }
}

func drawLine(_ x1: CGFloat, _ y1: CGFloat, _ x2: CGFloat, _ y2: CGFloat, dashed: Bool = false) {
    let path = NSBezierPath()
    path.move(to: point(x1, y1))
    path.line(to: point(x2, y2))
    path.lineWidth = 2.2
    if dashed {
        let pattern: [CGFloat] = [8, 6]
        path.setLineDash(pattern, count: pattern.count, phase: 0)
    }
    labelColor.setStroke()
    path.stroke()
}

func drawPolyline(_ points: [NSPoint]) {
    let path = NSBezierPath()
    path.move(to: point(points[0].x, points[0].y))
    for nextPoint in points.dropFirst() {
        path.line(to: point(nextPoint.x, nextPoint.y))
    }
    path.lineWidth = 2.2
    labelColor.setStroke()
    path.stroke()
}

func drawTriangle(points: [NSPoint]) {
    let triangle = NSBezierPath()
    triangle.move(to: points[0])
    triangle.line(to: points[1])
    triangle.line(to: points[2])
    triangle.close()
    NSColor.white.setFill()
    triangle.fill()
    borderColor.setStroke()
    triangle.lineWidth = 2
    triangle.stroke()
}

func drawMultiplicity(_ text: String, x: CGFloat, y: CGFloat) {
    drawText(text, x: x, y: y, width: 70, font: NSFont.systemFont(ofSize: 13), color: titleColor)
}

func drawAssociationLabel(_ text: String, x: CGFloat, y: CGFloat) {
    drawText(text, x: x, y: y, width: 120, font: NSFont.systemFont(ofSize: 14), color: labelColor)
}

let image = NSImage(size: NSSize(width: canvasWidth, height: canvasHeight))
image.lockFocus()

NSColor.white.setFill()
NSBezierPath(rect: NSRect(x: 0, y: 0, width: canvasWidth, height: canvasHeight)).fill()

drawText(
        "Advantis Dental Surgeries (ADS) Domain Model",
        x: 0,
        y: 8,
        width: canvasWidth,
        font: NSFont.boldSystemFont(ofSize: 24),
        color: titleColor,
        alignment: .center
)

drawClassBox(x: 520, y: 50, width: 560, height: 140, title: "Person", lines: [
    "- firstName : String",
    "- lastName : String",
    "- phoneNumber : String",
    "- email : String"
])

drawClassBox(x: 140, y: 250, width: 260, height: 120, title: "OfficeManager", lines: [
    "- officeManagerId : long"
])

drawClassBox(x: 550, y: 250, width: 270, height: 130, title: "Dentist", lines: [
    "- dentistId : long",
    "- specialization : String"
])

drawClassBox(x: 950, y: 250, width: 290, height: 145, title: "Patient", lines: [
    "- patientId : long",
    "- mailingAddress : String",
    "- dateOfBirth : LocalDate"
])

drawClassBox(x: 130, y: 500, width: 320, height: 175, title: "AppointmentRequest", lines: [
    "- requestId : long",
    "- requestChannel : RequestChannel",
    "- requestType : RequestType",
    "- preferredDateTime : LocalDateTime",
    "- status : RequestStatus"
])

drawClassBox(x: 550, y: 500, width: 340, height: 155, title: "Appointment", lines: [
    "- appointmentId : long",
    "- appointmentDateTime : LocalDateTime",
    "- status : AppointmentStatus",
    "- confirmationEmailSent : boolean"
])

drawClassBox(x: 1010, y: 500, width: 300, height: 135, title: "Surgery", lines: [
    "- surgeryId : long",
    "- name : String",
    "- locationAddress : String",
    "- telephoneNumber : String"
])

drawClassBox(x: 1020, y: 745, width: 320, height: 175, title: "DentalServiceBill", lines: [
    "- billId : long",
    "- issueDate : LocalDate",
    "- amount : BigDecimal",
    "- status : BillStatus",
    "- description : String"
])

drawNote(x: 120, y: 760, width: 360, height: 100, lines: [
    "Constraint:",
    "A patient with an outstanding unpaid",
    "bill cannot request a new appointment."
])

drawNote(x: 540, y: 760, width: 380, height: 100, lines: [
    "Constraint:",
    "A dentist cannot be assigned more than",
    "5 appointments in any given week."
])

drawLine(270, 250, 650, 190)
drawTriangle(points: [point(650, 190), point(636, 196), point(642, 182)])

drawLine(685, 250, 800, 190)
drawTriangle(points: [point(800, 190), point(786, 196), point(792, 182)])

drawLine(1095, 250, 950, 190)
drawTriangle(points: [point(950, 190), point(964, 196), point(958, 182)])

drawLine(290, 500, 290, 395)
drawAssociationLabel("processes", x: 302, y: 438)
drawMultiplicity("0..*", x: 266, y: 474)
drawMultiplicity("0..1", x: 252, y: 402)

drawLine(400, 334, 550, 550)
drawAssociationLabel("books", x: 435, y: 420)
drawMultiplicity("1", x: 392, y: 342)
drawMultiplicity("0..*", x: 528, y: 530)

drawLine(950, 335, 890, 575)
drawAssociationLabel("has", x: 905, y: 430)
drawMultiplicity("1", x: 930, y: 346)
drawMultiplicity("0..*", x: 900, y: 548)

drawPolyline([NSPoint(x: 450, y: 520), NSPoint(x: 500, y: 520), NSPoint(x: 500, y: 450), NSPoint(x: 920, y: 450), NSPoint(x: 920, y: 360), NSPoint(x: 950, y: 360)])
drawAssociationLabel("submittedBy", x: 690, y: 422)
drawMultiplicity("0..*", x: 430, y: 504)
drawMultiplicity("1", x: 924, y: 340)

drawLine(820, 565, 1010, 565)
drawAssociationLabel("scheduledAt", x: 887, y: 538)
drawMultiplicity("0..*", x: 836, y: 548)
drawMultiplicity("1", x: 982, y: 548)

drawLine(685, 380, 720, 500)
drawAssociationLabel("treats", x: 728, y: 425)
drawMultiplicity("1", x: 664, y: 384)
drawMultiplicity("0..*", x: 726, y: 478)

drawLine(450, 575, 550, 575)
drawAssociationLabel("resultsIn", x: 468, y: 546)
drawMultiplicity("0..*", x: 460, y: 558)
drawMultiplicity("0..1", x: 526, y: 558)

drawPolyline([NSPoint(x: 720, y: 655), NSPoint(x: 720, y: 720), NSPoint(x: 1020, y: 720), NSPoint(x: 1020, y: 780)])
drawAssociationLabel("generates", x: 824, y: 691)
drawMultiplicity("0..1", x: 696, y: 640)
drawMultiplicity("0..1", x: 996, y: 760)

drawPolyline([NSPoint(x: 1240, y: 325), NSPoint(x: 1400, y: 325), NSPoint(x: 1400, y: 820), NSPoint(x: 1340, y: 820)])
drawAssociationLabel("owes", x: 1320, y: 296)
drawMultiplicity("1", x: 1218, y: 306)
drawMultiplicity("0..*", x: 1350, y: 794)

drawLine(300, 760, 300, 655, dashed: true)
drawLine(730, 760, 730, 655, dashed: true)

image.unlockFocus()

guard
        let tiffData = image.tiffRepresentation,
        let bitmap = NSBitmapImageRep(data: tiffData),
        let pngData = bitmap.representation(using: .png, properties: [:])
else {
    fatalError("Unable to create PNG data.")
}

let scriptUrl = URL(fileURLWithPath: CommandLine.arguments[0])
let outputUrl = scriptUrl.deletingLastPathComponent().appendingPathComponent("ads-domain-model.png")

try pngData.write(to: outputUrl)
print("Wrote PNG to \(outputUrl.path)")
